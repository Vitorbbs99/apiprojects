package com.javaapi.pmanager.infrastructure.controller;

import com.javaapi.pmanager.domain.applicationservice.MessagePublisher;
import com.javaapi.pmanager.domain.events.HelloWorldEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TesteResourceMQ {

    private final MessagePublisher publisher;

    public TesteResourceMQ(MessagePublisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/enviar")
    public String enviar(@RequestParam(defaultValue = "Olá RabbitMQ!") String mensagem) {

        HelloWorldEvent event = new HelloWorldEvent(
                mensagem,
                LocalDateTime.now()
        );

        publisher.publish(event);

        return "Mensagem enviada para a fila: " + mensagem;
    }
}