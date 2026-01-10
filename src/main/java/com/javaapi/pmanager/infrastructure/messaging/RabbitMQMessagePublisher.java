package com.javaapi.pmanager.infrastructure.messaging;

import com.javaapi.pmanager.domain.applicationservice.ports.MessagePublisher;
import com.javaapi.pmanager.domain.events.HelloWorldEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQMessagePublisher implements MessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(HelloWorldEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCH_HELLO_WORLD, "", event);
    }
}