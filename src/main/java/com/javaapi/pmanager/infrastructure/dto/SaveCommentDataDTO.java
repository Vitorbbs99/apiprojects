package com.javaapi.pmanager.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SaveCommentDataDTO(
        @NotBlank(message = "Text cannot be empty")
        @Size(min = 1, max = 150, message = "Invalid text")
        String text,
        String taskId,
        String memberId
) {}