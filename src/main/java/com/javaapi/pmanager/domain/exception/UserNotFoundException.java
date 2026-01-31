package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class UserNotFoundException extends RequestException {
    public UserNotFoundException(String userId) {
        super("UserNotFound", "User not found: " + userId);
    }
}
