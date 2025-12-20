package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class InvalidProjectStatusExcpetion extends RequestException {
    public InvalidProjectStatusExcpetion(String statusStr) {
        super("InvalidProjectStatus", "Invalid project status: " + statusStr);
    }
}
