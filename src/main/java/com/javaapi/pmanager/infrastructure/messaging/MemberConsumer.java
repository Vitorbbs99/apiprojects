package com.javaapi.pmanager.infrastructure.messaging;

import com.javaapi.pmanager.domain.events.MemberCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MemberConsumer {

    private static final Logger log = LoggerFactory.getLogger(MemberConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PEDIDOS)
    public void onMemberCreated(MemberCreatedEvent event) {
        // Simulamos o processamento do Log
        log.info(">>>> [LOG DO SISTEMA] Novo membro registado com sucesso!");
        log.info("ID: {} | Nome: {} | Email: {}", event.id(), event.name(), event.email());
        log.info("----------------------------------------------------------");
    }
}
