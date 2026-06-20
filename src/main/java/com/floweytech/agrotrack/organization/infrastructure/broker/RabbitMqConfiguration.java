package com.floweytech.agrotrack.organization.infrastructure.broker;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Bean
    public DirectExchange agrotrackExchange() {
        return new DirectExchange(SubscriptionRabbitTopology.EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange agrotrackDeadLetterExchange() {
        return new DirectExchange(SubscriptionRabbitTopology.DEAD_LETTER_EXCHANGE, true, false);
    }

    @Bean
    public Queue organizationSubscriptionQueue() {
        return QueueBuilder.durable(SubscriptionRabbitTopology.QUEUE)
                .deadLetterExchange(SubscriptionRabbitTopology.DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(SubscriptionRabbitTopology.DEAD_LETTER_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue organizationSubscriptionDeadLetterQueue() {
        return QueueBuilder.durable(SubscriptionRabbitTopology.DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public Binding subscriptionCreatedBinding() {
        return bindSubscriptionQueue(SubscriptionRabbitTopology.CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding subscriptionActivatedBinding() {
        return bindSubscriptionQueue(SubscriptionRabbitTopology.ACTIVATED_ROUTING_KEY);
    }

    @Bean
    public Binding subscriptionCancelledBinding() {
        return bindSubscriptionQueue(SubscriptionRabbitTopology.CANCELLED_ROUTING_KEY);
    }

    @Bean
    public Binding subscriptionExpiredBinding() {
        return bindSubscriptionQueue(SubscriptionRabbitTopology.EXPIRED_ROUTING_KEY);
    }

    @Bean
    public Binding subscriptionDeadLetterBinding() {
        return BindingBuilder.bind(organizationSubscriptionDeadLetterQueue())
                .to(agrotrackDeadLetterExchange())
                .with(SubscriptionRabbitTopology.DEAD_LETTER_ROUTING_KEY);
    }

    private Binding bindSubscriptionQueue(String routingKey) {
        return BindingBuilder.bind(organizationSubscriptionQueue())
                .to(agrotrackExchange())
                .with(routingKey);
    }
}
