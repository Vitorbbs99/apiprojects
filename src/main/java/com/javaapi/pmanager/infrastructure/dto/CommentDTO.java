package com.javaapi.pmanager.infrastructure.dto;

import com.javaapi.pmanager.domain.entity.Comment;

public record CommentDTO(String id, String text) {
    public static CommentDTO create(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getText()
        );
    }
}