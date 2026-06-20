package com.floweytech.agrotrack.organization.application.internal.eventhandlers;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Organization;
import com.floweytech.agrotrack.organization.domain.model.commands.CreateOrganizationCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SubscriptionId;
import com.floweytech.agrotrack.organization.infrastructure.broker.SubscriptionRabbitTopology;
import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import com.floweytech.agrotrack.organization.shared.events.SubscriptionActivatedEvent;
import com.floweytech.agrotrack.organization.shared.events.SubscriptionCancelledEvent;
import com.floweytech.agrotrack.organization.shared.events.SubscriptionCreatedEvent;
import com.floweytech.agrotrack.organization.shared.events.SubscriptionExpiredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

@Service
public class SubscriptionEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionEventHandler.class);

    private final OrganizationRepository organizationRepository;
    private final ObjectMapper objectMapper;

    public SubscriptionEventHandler(OrganizationRepository organizationRepository, ObjectMapper objectMapper) {
        this.organizationRepository = organizationRepository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = SubscriptionRabbitTopology.QUEUE)
    @Transactional
    public void onMessage(Message message) {
        var routingKey = message.getMessageProperties().getReceivedRoutingKey();
        LOGGER.info("Received RabbitMQ event {} with messageId {}", routingKey,
                message.getMessageProperties().getMessageId());

        switch (routingKey) {
            case SubscriptionRabbitTopology.CREATED_ROUTING_KEY ->
                    on(read(message, SubscriptionCreatedEvent.class));
            case SubscriptionRabbitTopology.ACTIVATED_ROUTING_KEY ->
                    on(read(message, SubscriptionActivatedEvent.class));
            case SubscriptionRabbitTopology.CANCELLED_ROUTING_KEY ->
                    on(read(message, SubscriptionCancelledEvent.class));
            case SubscriptionRabbitTopology.EXPIRED_ROUTING_KEY ->
                    on(read(message, SubscriptionExpiredEvent.class));
            default -> throw new IllegalArgumentException("Unsupported routing key: " + routingKey);
        }
    }

    private void on(SubscriptionCreatedEvent event) {
        var subscriptionId = new SubscriptionId(event.subscriptionId());
        if (organizationRepository.findBySubscriptionId(subscriptionId).isPresent()) {
            LOGGER.info("Ignoring duplicate subscription.created for subscriptionId {}", event.subscriptionId());
            return;
        }

        var command = new CreateOrganizationCommand(
            event.organizationName(),
            event.maxPlots(),
            event.ownerUserId(),
            event.subscriptionId()
        );

        var organization = new Organization(command);
        organizationRepository.saveAndFlush(organization);
        LOGGER.info("Created organization for subscriptionId {}", event.subscriptionId());
    }

    private void on(SubscriptionActivatedEvent event) {
        var subscriptionId = new SubscriptionId(event.subscriptionId());
        var organization = findOrganization(subscriptionId);
        organization.activate();
        organizationRepository.save(organization);
    }

    private void on(SubscriptionExpiredEvent event) {
        var subscriptionId = new SubscriptionId(event.subscriptionId());
        var organization = findOrganization(subscriptionId);
        organization.deactivate();
        organizationRepository.save(organization);
    }

    private void on(SubscriptionCancelledEvent event) {
        var subscriptionId = new SubscriptionId(event.subscriptionId());
        var organization = findOrganization(subscriptionId);
        organization.deactivate();
        organizationRepository.save(organization);
    }

    private Organization findOrganization(SubscriptionId subscriptionId) {
        return organizationRepository.findBySubscriptionId(subscriptionId)
                .orElseThrow(() -> new IllegalStateException(
                        "Organization not found for subscriptionId " + subscriptionId.value()));
    }

    private <T> T read(Message message, Class<T> eventType) {
        try {
            return objectMapper.readValue(message.getBody(), eventType);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Invalid " + eventType.getSimpleName() + " payload", exception);
        }
    }
}
