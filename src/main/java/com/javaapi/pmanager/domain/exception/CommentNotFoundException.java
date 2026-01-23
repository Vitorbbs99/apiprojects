package com.javaapi.pmanager.domain.exception;

import com.javaapi.pmanager.infrastructure.exception.RequestException;

public class CommentNotFoundException extends RequestException {
    public CommentNotFoundException(String commentId) {
        super("CommentNotFound", "Comment not found: " + commentId);
    }
}
