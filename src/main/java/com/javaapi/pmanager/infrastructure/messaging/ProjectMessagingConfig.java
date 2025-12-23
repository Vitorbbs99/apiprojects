package com.javaapi.pmanager.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;

public class ProjectMessagingConfig {

    public static final String EXCHANGE_PROJECT = "exch.project";
    public static final String QUEUE_PROJECT = "q.project";

    @Bean
    public FanoutExchange projectExchange() {
        return new FanoutExchange(EXCHANGE_PROJECT);
    }

    @Bean
    public Queue projectQueue() {
        return new Queue(QUEUE_PROJECT, true);
    }

    @Bean
    public Binding projectBinding() {
        return BindingBuilder.bind(projectQueue()).to(projectExchange());
    }
}
