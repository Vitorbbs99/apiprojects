package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class TaskNotFoundException extends RequestException {
    public TaskNotFoundException(String taskId) {
        super("TaskNotFound", "Task not found: " + taskId);
    }
}
