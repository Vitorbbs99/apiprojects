package com.javaapi.pmanager.infrastructure.dto;

import com.javaapi.pmanager.domain.entity.Task;
import com.javaapi.pmanager.domain.model.TaskStatus;
import lombok.*;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private String id;
    private String title;
    private String description;
    private Integer numberOfDays;
    private TaskStatus status;
    private ProjectDTO project;
    private UserDTO assignedMember;

    public static TaskDTO create(Task task) {
        return new TaskDTO(
            task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getNumberOfDays(),
                task.getStatus(),
                Optional.ofNullable(task.getProject()).map(ProjectDTO::create).orElse(null),
                Optional.ofNullable(task.getAssignedUser()).map(UserDTO::create).orElse(null)
        );
    }
}
