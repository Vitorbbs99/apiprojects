package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.entity.Project;
import com.javaapi.pmanager.domain.entity.Task;
import com.javaapi.pmanager.domain.entity.User;
import com.javaapi.pmanager.domain.events.ProjectStatsEvent;
import com.javaapi.pmanager.domain.model.ProjectStatsEventType;
import com.javaapi.pmanager.domain.exception.DuplicateTaskException;
import com.javaapi.pmanager.domain.exception.InvalidTaskStatusExcpetion;
import com.javaapi.pmanager.domain.exception.TaskNotFoundException;
import com.javaapi.pmanager.domain.model.TaskStatus;
import com.javaapi.pmanager.domain.repository.TaskRepository;
import com.javaapi.pmanager.infrastructure.config.AppConfigProperties;
import com.javaapi.pmanager.infrastructure.dto.SaveTaskDataDTO;
import com.javaapi.pmanager.infrastructure.dto.TaskDTO;
import com.javaapi.pmanager.infrastructure.dto.TaskListDTO;
import com.javaapi.pmanager.infrastructure.dto.TaskResponse;
import com.javaapi.pmanager.infrastructure.services.FileService;
import com.javaapi.pmanager.infrastructure.services.KafkaProducerService;
import com.javaapi.pmanager.infrastructure.util.PaginationHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.javaapi.pmanager.domain.model.TaskStatus.FINISHED;


@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final UserService userService;
    private final AppConfigProperties props;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private FileService fileService;

    @Transactional
    @CacheEvict(value = "task", allEntries = true)
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

        // CRIA O TÓPICO NO KAFKA
        var evento = new ProjectStatsEvent(
                project.getId(),
                ProjectStatsEventType.TASK_CREATED,
                0,
                LocalDateTime.now()
        );

        kafkaProducerService.sendMessage("update-project-stats", evento);

        return task;
    }

    public Task loadTask(String taskId) {
        return taskRepository
                .findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }

    @Transactional
    @CacheEvict(value = "task", allEntries = true)
    public void deleteTask(String taskId) {
        Task task = loadTask(taskId);
        taskRepository.delete(task);
    }

    @Transactional
    @CacheEvict(value = "task", allEntries = true)
    public Task updateTask(String taskId, SaveTaskDataDTO saveTaskData) {

        Project project = getProjectIfPossible(saveTaskData.getProjectId());
        User user = getMemberIfPossible(saveTaskData.getUserId());

        if (existisTaskWithName(saveTaskData.getTitle(), taskId)) {
            throw new DuplicateTaskException(saveTaskData.getTitle());
        }

        Task task = loadTask(taskId);

        TaskStatus statusOld = task.getStatus();
        TaskStatus statusNew = convertToTaskStatus(saveTaskData.getStatus());

        task.setTitle(saveTaskData.getTitle());
        task.setDescription(saveTaskData.getDescription());
        task.setNumberOfDays(saveTaskData.getNumberOfDays());
        task.setStatus(statusNew);
        task.setProject(project);
        task.setAssignedUser(user);

        // CRIA O TÓPICO NO KAFKA
        if (statusOld != FINISHED && statusNew == FINISHED) {
            var evento = new ProjectStatsEvent(
                    project.getId(),
                    ProjectStatsEventType.TASK_UPDATE,
                    0,
                    LocalDateTime.now()
            );
            kafkaProducerService.sendMessage("update-project-stats", evento);
        }

        return task;
    }

    @Transactional
    public String updateTaskFile(String taskId, MultipartFile file) {
        Task task = loadTask(taskId);
        String fileName = fileService.storeFile(file);

        task.setFile(fileName);

        return fileName;
    }

    @Cacheable(value = "task", key = "'tasks-list-' + #projectId + '-' + #page")
    public TaskResponse findTasks (
            String statusStr,
            String partialTitle,
            Integer page,
            String directionStr,
            List<String> properties
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "title");

        Page<Task> taskPage = taskRepository.find(
                Optional.ofNullable(statusStr).map(this::convertToTaskStatus).orElse(null),
                partialTitle,
                PaginationHelper.createPageable(page, props.getGeneral().getPageSize(), directionStr, properties)
        );

        List<TaskListDTO> dtos = taskPage.getContent().stream()
                .map(TaskListDTO::create)
                .toList();

        return new TaskResponse(
                dtos,
                taskPage.getTotalElements(),
                taskPage.getTotalPages(),
                taskPage.getNumber()
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
