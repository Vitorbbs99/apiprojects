package com.javaapi.pmanager.infrastructure.messaging;

import com.javaapi.pmanager.domain.events.HelloWorldEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQHelloWorldConsumer {

    // O @RabbitListener diz ao Spring para monitorar a fila específica
    @RabbitListener(queues = RabbitMQConfig.QUEUE_HELLO_WORLD)
    public void receiveMessage(HelloWorldEvent event) {

        // Lógica de negócio (ex: chamar um Service)
        System.out.println("------------------------------------------------");
        System.out.println("MENSAGEM RECEBIDA VIA RABBITMQ!");
        System.out.println("Conteúdo: " + event.message());
        System.out.println("Gerada em: " + event.createdAt());
        System.out.println("------------------------------------------------");

        // Enviar um e-mail de boas-vindas
    }
}