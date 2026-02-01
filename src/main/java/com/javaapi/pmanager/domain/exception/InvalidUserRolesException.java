package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class InvalidUserRolesException extends RequestException {
    public InvalidUserRolesException(String rolesStr) {
        super("InvalidUserRole", "Invalid user role: " + rolesStr);
    }
}
