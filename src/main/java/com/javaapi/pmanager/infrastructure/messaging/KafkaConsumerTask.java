package com.javaapi.pmanager.infrastructure.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerTask {

    @KafkaListener(topics = "task-stats-topic", groupId = "pmanager-group")
    public void consume(String message) {
        System.out.println("=== NOVO EVENTO RECEBIDO NO KAFKA ===");
        System.out.println("Conteúdo da mensagem: " + message);
        System.out.println("=====================================");
    }
}
