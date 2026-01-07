package com.javaapi.pmanager.infrastructure.messaging;

import com.javaapi.pmanager.domain.events.ProjectCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProjectConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProjectConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PROJECT)
    public void onProjectCreated(ProjectCreatedEvent event) {
        // Simulamos o processamento do Log
        log.info(">>>> [LOG DO SISTEMA] Novo projeto registado com sucesso!");
        log.info("ID: {} | Nome: {} | Email: {} | Data de criação: {} | Data final: {}", event.id(), event.name(), event.description(),event.initialDate(), event.finalDate());
        log.info("----------------------------------------------------------");
    }
}
