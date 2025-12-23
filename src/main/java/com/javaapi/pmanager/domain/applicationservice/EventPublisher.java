package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.events.MemberCreatedEvent;

public interface EventPublisher {
    void publish(MemberCreatedEvent event);
}
