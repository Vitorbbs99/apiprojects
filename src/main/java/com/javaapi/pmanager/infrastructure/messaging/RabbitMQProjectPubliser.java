package com.javaapi.pmanager.infrastructure.messaging;

import com.javaapi.pmanager.domain.applicationservice.ports.ProjectPublisher;
import com.javaapi.pmanager.domain.events.ProjectCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQProjectPubliser implements ProjectPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQProjectPubliser(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(ProjectCreatedEvent event) {
        // Usa a Exchange que difini em RabbitMQConfig
        rabbitTemplate.convertAndSend(ProjectMessagingConfig.EXCHANGE_PROJECT, "", event);
    }
}
