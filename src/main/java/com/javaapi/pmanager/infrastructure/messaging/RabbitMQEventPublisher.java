package com.javaapi.pmanager.infrastructure.messaging;

import com.javaapi.pmanager.domain.applicationservice.EventPublisher;
import com.javaapi.pmanager.domain.events.MemberCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQEventPublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(MemberCreatedEvent event) {
        // Usa a Exchange que difini em RabbitMQConfig
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_PEDIDOS, "", event);
    }
}
