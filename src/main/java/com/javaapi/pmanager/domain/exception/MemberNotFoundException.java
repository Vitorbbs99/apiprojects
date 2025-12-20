package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class MemberNotFoundException extends RequestException {
    public MemberNotFoundException(String memberId) {
        super("MemberNotFound", "Member not found: " + memberId);
    }
}
