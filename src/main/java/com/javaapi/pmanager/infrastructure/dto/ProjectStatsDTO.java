package com.javaapi.pmanager.infrastructure.dto;

public record ProjectStatsDTO(
        long countTasks,
        long countTasksPending
) {
}
