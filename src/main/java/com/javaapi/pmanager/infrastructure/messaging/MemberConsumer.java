package com.javaapi.pmanager.infrastructure.messaging;

import com.javaapi.pmanager.domain.events.MemberCreatedEvent;
import com.javaapi.pmanager.infrastructure.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MemberConsumer {

    private static final Logger log = LoggerFactory.getLogger(MemberConsumer.class);
    private final EmailService emailService; // Injetando o serviço de e-mail

    public MemberConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PEDIDOS)
    public void onMemberCreated(MemberCreatedEvent event) {
        // Processamento do Log
        System.out.println("Processando envio de e-mail para: " + event.email());

        //Envio de e-mail
        emailService.enviarEmailBoasVindasHtml(event.email(), event.name());
    }
}
