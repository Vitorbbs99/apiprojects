package com.javaapi.pmanager.infrastructure.services;

import com.javaapi.pmanager.domain.events.ProjectStatsEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, ProjectStatsEvent event) {
        kafkaTemplate.send(topic, event);
        System.out.println("Mensagem enviada para o Kafka: " + event);
    }
}
