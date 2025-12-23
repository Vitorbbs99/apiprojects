package com.javaapi.pmanager.domain.events;

public record ProjectCreatedEvent (
        String id,
        String name,
        String description
) {}