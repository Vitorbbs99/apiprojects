package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class DuplicateUserException extends RequestException {
    public DuplicateUserException(String email) {
        super("EmailDuplicated", "A user with the e-mail already exists: " + email);
    }
}
