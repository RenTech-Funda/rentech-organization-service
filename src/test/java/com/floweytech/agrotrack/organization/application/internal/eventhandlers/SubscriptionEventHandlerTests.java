package com.floweytech.agrotrack.organization.application.internal.eventhandlers;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Organization;
import com.floweytech.agrotrack.organization.domain.model.commands.CreateOrganizationCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SubscriptionId;
import com.floweytech.agrotrack.organization.infrastructure.broker.SubscriptionRabbitTopology;
import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import com.floweytech.agrotrack.organization.shared.events.SubscriptionActivatedEvent;
import com.floweytech.agrotrack.organization.shared.events.SubscriptionCreatedEvent;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import tools.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SubscriptionEventHandlerTests {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldCreateOrganizationFromCreatedEvent() throws Exception {
        var repository = mock(OrganizationRepository.class);
        when(repository.findBySubscriptionId(any(SubscriptionId.class))).thenReturn(Optional.empty());
        var handler = new SubscriptionEventHandler(repository, objectMapper);
        var event = new SubscriptionCreatedEvent(25L, "Fundo Norte", 30, 7L);

        handler.onMessage(message(event, SubscriptionRabbitTopology.CREATED_ROUTING_KEY));

        var organizationCaptor = ArgumentCaptor.forClass(Organization.class);
        verify(repository).saveAndFlush(organizationCaptor.capture());
        var organization = organizationCaptor.getValue();
        assertEquals("Fundo Norte", organization.getOrganizationName());
        assertEquals(30, organization.getMaxPlots());
        assertEquals(7L, organization.getOwnerUserId().value());
        assertEquals(25L, organization.getSubscriptionId().value());
    }

    @Test
    void shouldIgnoreDuplicateCreatedEvent() throws Exception {
        var repository = mock(OrganizationRepository.class);
        var existing = new Organization(new CreateOrganizationCommand("Existing", 10, 7L, 25L));
        when(repository.findBySubscriptionId(any(SubscriptionId.class))).thenReturn(Optional.of(existing));
        var handler = new SubscriptionEventHandler(repository, objectMapper);

        handler.onMessage(message(
                new SubscriptionCreatedEvent(25L, "Fundo Norte", 30, 7L),
                SubscriptionRabbitTopology.CREATED_ROUTING_KEY));

        verify(repository, never()).saveAndFlush(any(Organization.class));
    }

    @Test
    void shouldActivateOrganizationFromActivatedEvent() throws Exception {
        var repository = mock(OrganizationRepository.class);
        var organization = new Organization(new CreateOrganizationCommand("Fundo Norte", 30, 7L, 25L));
        when(repository.findBySubscriptionId(any(SubscriptionId.class))).thenReturn(Optional.of(organization));
        var handler = new SubscriptionEventHandler(repository, objectMapper);

        handler.onMessage(message(
                new SubscriptionActivatedEvent(25L),
                SubscriptionRabbitTopology.ACTIVATED_ROUTING_KEY));

        assertTrue(organization.getIsActive());
        verify(repository).save(organization);
    }

    private Message message(Object event, String routingKey) throws Exception {
        var message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(event)).build();
        message.getMessageProperties().setReceivedRoutingKey(routingKey);
        message.getMessageProperties().setMessageId("test-message-id");
        return message;
    }
}
