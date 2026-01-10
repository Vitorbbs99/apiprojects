package com.javaapi.pmanager.domain.applicationservice.ports;

import com.javaapi.pmanager.domain.events.ProjectCreatedEvent;

public interface ProjectPublisher {
    void publish(ProjectCreatedEvent event);
}
