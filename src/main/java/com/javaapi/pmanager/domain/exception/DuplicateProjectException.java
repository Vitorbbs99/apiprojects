package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class DuplicateProjectException extends RequestException {
    public DuplicateProjectException(String name) {
        super("ProjectDuplicate", "A Project with the name already exists: " + name);
    }
}
