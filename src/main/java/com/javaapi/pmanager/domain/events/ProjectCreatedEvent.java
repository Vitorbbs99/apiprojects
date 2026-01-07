package com.javaapi.pmanager.domain.events;

import java.time.LocalDate;

public record ProjectCreatedEvent (
        String id,
        String name,
        String description,
        LocalDate initialDate,
        LocalDate finalDate
) {}