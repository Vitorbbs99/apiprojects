package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.entity.Member;
import com.javaapi.pmanager.domain.entity.Project;
import com.javaapi.pmanager.domain.entity.Task;
import com.javaapi.pmanager.domain.entity.User;
import com.javaapi.pmanager.domain.exception.DuplicateTaskException;
import com.javaapi.pmanager.domain.exception.InvalidTaskStatusExcpetion;
import com.javaapi.pmanager.domain.exception.TaskNotFoundException;
import com.javaapi.pmanager.domain.model.TaskStatus;
import com.javaapi.pmanager.domain.repository.TaskRepository;
import com.javaapi.pmanager.infrastructure.config.AppConfigProperties;
import com.javaapi.pmanager.infrastructure.dto.SaveTaskDataDTO;
import com.javaapi.pmanager.infrastructure.services.FileService;
import com.javaapi.pmanager.infrastructure.util.PaginationHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final UserService userService;
    private final AppConfigProperties props;

    @Autowired
    private FileService fileService;

    @Transactional
    public Task createTask(SaveTaskDataDTO saveTaskData) {

        Project project = getProjectIfPossible(saveTaskData.getProjectId());
        User user = getMemberIfPossible(saveTaskData.getUserId());

        if (existisTaskWithName(saveTaskData.getTitle(), null)) {
            throw new DuplicateTaskException(saveTaskData.getTitle());
        }
        Task task = Task
                .builder()
                .title(saveTaskData.getTitle())
                .description(saveTaskData.getDescription())
                .numberOfDays(saveTaskData.getNumberOfDays())
                .status(TaskStatus.PENDING)
                .project(project)
                .assignedUser(user)
                .build();

        taskRepository.save(task);

        log.info("Task created: " + task);
        return task;
    }

    public Task loadTask(String taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Transactional
    public void deleteTask(String taskId) {
        Task task = loadTask(taskId);
        taskRepository.delete(task);
    }

    @Transactional
    public Task updateTask(String taskId, SaveTaskDataDTO saveTaskData) {

        Project project = getProjectIfPossible(saveTaskData.getProjectId());
        User user = getMemberIfPossible(saveTaskData.getUserId());

        if (existisTaskWithName(saveTaskData.getTitle(), taskId)) {
            throw new DuplicateTaskException(saveTaskData.getTitle());
        }
        Task task = loadTask(taskId);

        task.setTitle(saveTaskData.getTitle());
        task.setDescription(saveTaskData.getDescription());
        task.setNumberOfDays(saveTaskData.getNumberOfDays());
        task.setStatus(convertToTaskStatus(saveTaskData.getStatus()));
        task.setProject(project);
        task.setAssignedUser(user);

        return task;
    }

    @Transactional
    public String updateTaskFile(String taskId, MultipartFile file) {
        Task task = loadTask(taskId);
        String fileName = fileService.storeFile(file);

        task.setFile(fileName);

        return fileName;
    }

    public Page<Task> findTasks (
            String projectId,
            String memberId,
            String statusStr,
            String partialTitle,
            Integer page,
            String directionStr,
            List<String> properties
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "title");

        return taskRepository.find(
                projectId,
                memberId,
                Optional.ofNullable(statusStr).map(this::convertToTaskStatus).orElse(null),
                partialTitle,
                PaginationHelper.createPageable(page, props.getGeneral().getPageSize(), directionStr, properties)
        );
    }

    private TaskStatus convertToTaskStatus (String statusStr) {
        try {
            return TaskStatus.valueOf(statusStr);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidTaskStatusExcpetion(statusStr);
        }
    }

    private boolean existisTaskWithName(String title, String idToExclude) {
        return taskRepository
                .findByTitle(title)
                .filter(t -> !Objects.equals(t.getId(), idToExclude))
                .isPresent();
    }

    /*public List<Task> findTasks() {
        List<Task> tasks;

        tasks = taskRepository.findAll();

        return tasks;
    }*/


    private User getMemberIfPossible(String memberId) {
        User user = null;
        if (!Objects.isNull(memberId)) {
            user = userService.loadUserById(memberId);
        }
        return user;
    }

    private Project getProjectIfPossible(String projectId) {
        Project project = null;
        if (!Objects.isNull(projectId)) {
            project = projectService.loadProject(projectId);
        }
        return project;
    }

}
