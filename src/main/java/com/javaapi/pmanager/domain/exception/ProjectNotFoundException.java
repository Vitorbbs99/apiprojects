package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class ProjectNotFoundException extends RequestException {
    public ProjectNotFoundException(String projectId) {
        super("ProjectNotFound", "Project not found: " + projectId);
    }
}
