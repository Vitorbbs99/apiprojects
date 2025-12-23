package com.javaapi.pmanager.domain.applicationservice;

import com.javaapi.pmanager.domain.events.HelloWorldEvent;

public interface MessagePublisher {
    void publish(HelloWorldEvent event);
}
