package com.javaapi.pmanager.infrastructure.controller;

import com.javaapi.pmanager.domain.applicationservice.TaskService;
import com.javaapi.pmanager.domain.entity.Member;
import com.javaapi.pmanager.domain.entity.Task;
import com.javaapi.pmanager.infrastructure.dto.MemberDTO;
import com.javaapi.pmanager.infrastructure.dto.TaskDTO;
import com.javaapi.pmanager.infrastructure.dto.SaveTaskDataDTO;
import com.javaapi.pmanager.infrastructure.util.SortProperties;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.javaapi.pmanager.infrastructure.controller.RestConstants.PATH_TASKS;

@RestController
@RequestMapping(PATH_TASKS)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class TaskRestResource {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid SaveTaskDataDTO saveTaskDataDTO) {
        Task task = taskService.createTask(saveTaskDataDTO);

        return ResponseEntity
                .created(URI.create(PATH_TASKS + "/" + task.getId()))
                .body(TaskDTO.create(task));
    }

    // URL/tasks/id-do-projeto
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> loadTask(@PathVariable("id") String taskId) {
        Task task = taskService.loadTask(taskId);
        return ResponseEntity.ok(TaskDTO.create(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") String taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
        @PathVariable("id") String taskId,
        @RequestBody @Valid SaveTaskDataDTO saveTaskDataDTO
    ) {
        Task task = taskService.updateTask(taskId, saveTaskDataDTO);
        return ResponseEntity.ok(TaskDTO.create(task));
    }


    @GetMapping
    public ResponseEntity<List<TaskDTO>> findTasks(
           @RequestParam(value = "projectId", required = false) String projectId,
           @RequestParam(value = "memberId", required = false) String memberId,
           @RequestParam(value = "status", required = false)  String status,
           @RequestParam(value = "partialTitle", required = false) String partialTitle,
           @RequestParam(value = "page", required = false) Integer page,
           @RequestParam(value = "direction", required = false) String direction,
           @RequestParam(value = "sort", required = false) SortProperties properties
    ){
       Page<Task> tasks = taskService.findTasks(
               projectId,
               memberId,
               status,
               partialTitle,
               page,
               direction,
               Optional
                       .ofNullable(properties)
                       .map(p -> p.getSortPropertiesList())
                       .orElse(List.of())
               );

       return ResponseEntity.ok(tasks.stream().map(TaskDTO::create).toList());
    }
}
