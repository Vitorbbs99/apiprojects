package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class DuplicateTaskException extends RequestException {
    public DuplicateTaskException(String title) {
        super("TaskDuplicated", "A Task with the name already exists: " + title);
    }
}
