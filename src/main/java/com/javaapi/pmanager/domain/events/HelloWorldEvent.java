package com.javaapi.pmanager.domain.events;

import java.time.LocalDateTime;

public record HelloWorldEvent(
        String message,
        LocalDateTime createdAt
) {}