package com.javaapi.pmanager.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig extends ProjectMessagingConfig {

    public static final String EXCH_HELLO_WORLD = "exch.hello-world";
    public static final String QUEUE_HELLO_WORLD = "q.hello-world";

    public static final String EXCHANGE_PEDIDOS = "exch.pedidos";
    public static final String QUEUE_PEDIDOS = "q.pedidos";

    @Bean
    public FanoutExchange helloWorldExchange() {
        return new FanoutExchange(EXCH_HELLO_WORLD);
    }

    @Bean
    public Queue helloWorldQueue() {
        return new Queue(QUEUE_HELLO_WORLD, true);
    }

    @Bean
    public Binding helloWorldBinding() {
        return BindingBuilder.bind(helloWorldQueue()).to(helloWorldExchange());
    }

    @Bean
    public FanoutExchange pedidosExchange() {
        return new FanoutExchange(EXCHANGE_PEDIDOS);
    }

    @Bean
    public Queue pedidosQeue() {
        return new Queue(QUEUE_PEDIDOS, true);
    }

    @Bean
    public Binding pedidosBinding() {
        return BindingBuilder.bind(pedidosQeue()).to(pedidosExchange());
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.declareExchange(helloWorldExchange());
        admin.declareQueue(helloWorldQueue());
        admin.declareBinding(helloWorldBinding());

        admin.declareExchange(pedidosExchange());
        admin.declareQueue(pedidosQeue());
        admin.declareBinding(pedidosBinding());

        admin.declareExchange(projectExchange());
        admin.declareQueue(projectQueue());
        admin.declareBinding(projectBinding());

        return admin;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}