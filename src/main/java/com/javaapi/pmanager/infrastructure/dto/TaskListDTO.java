package com.javaapi.pmanager.infrastructure.dto;

import com.javaapi.pmanager.domain.entity.Task;
import com.javaapi.pmanager.domain.model.TaskStatus;

public record TaskListDTO (
        String id,
        String title,
        String description,
        Integer numberOfDays,
        TaskStatus status
) {
    public static TaskListDTO create(Task task) {
        return new TaskListDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getNumberOfDays(),
                task.getStatus()
        );
    }
}
