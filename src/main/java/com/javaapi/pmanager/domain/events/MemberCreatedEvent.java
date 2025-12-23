package com.javaapi.pmanager.domain.events;

public record MemberCreatedEvent(
    String id,
    String name,
    String email
) {}
