package com.javaapi.pmanager.domain.events;

import com.javaapi.pmanager.domain.model.ProjectStatsEventType;

import java.time.LocalDateTime;

public record ProjectStatsEvent (
        String projectId,
        ProjectStatsEventType type,
        LocalDateTime timestamp
) {}
