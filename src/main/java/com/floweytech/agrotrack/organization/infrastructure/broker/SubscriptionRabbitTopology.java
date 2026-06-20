package com.floweytech.agrotrack.organization.infrastructure.broker;

public final class SubscriptionRabbitTopology {

    public static final String EXCHANGE = "agrotrack.exchange";
    public static final String QUEUE = "organization.subscription.queue";
    public static final String CREATED_ROUTING_KEY = "subscription.created";
    public static final String ACTIVATED_ROUTING_KEY = "subscription.activated";
    public static final String CANCELLED_ROUTING_KEY = "subscription.cancelled";
    public static final String EXPIRED_ROUTING_KEY = "subscription.expired";

    public static final String DEAD_LETTER_EXCHANGE = "agrotrack.dlx";
    public static final String DEAD_LETTER_QUEUE = "organization.subscription.dlq";
    public static final String DEAD_LETTER_ROUTING_KEY = "organization.subscription.dead";

    private SubscriptionRabbitTopology() {
    }
}
