package com.javaapi.pmanager.infrastructure.dto;

import java.util.List;

public record TaskResponse(
        List<TaskListDTO> content,
        long totalElements,
        int totalPages,
        int currentPage
) {}
