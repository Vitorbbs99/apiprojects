package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class InvalidTaskStatusExcpetion extends RequestException {
    public InvalidTaskStatusExcpetion(String statusStr) {
        super("InvalidTaskStatus", "Invalid task status: " + statusStr);
    }
}
