package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class DuplicateMemberException extends RequestException {
    public DuplicateMemberException(String email) {
        super("EmailDuplicated", "A Member with the e-mail already exists: " + email);
    }
}
