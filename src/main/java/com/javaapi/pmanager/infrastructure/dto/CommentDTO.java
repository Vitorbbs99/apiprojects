package com.javaapi.pmanager.infrastructure.dto;

import com.javaapi.pmanager.domain.entity.Comment;

public record CommentDTO(String id, String text, TaskDTO task, MemberDTO memberDTO) {
    public static CommentDTO create(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getText(),
                comment.getTask() != null ? TaskDTO.create(comment.getTask()) : null,
                comment.getAssignedMember() != null ? MemberDTO.create(comment.getAssignedMember()) : null
        );
    }
}