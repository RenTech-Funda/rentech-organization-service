package com.floweytech.agrotrack.organization.application.internal.eventhandlers;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Organization;
import com.floweytech.agrotrack.organization.domain.model.commands.CreateOrganizationCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SubscriptionId;
import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import com.floweytech.agrotrack.organization.shared.events.SubscriptionActivatedEvent;
import com.floweytech.agrotrack.organization.shared.events.SubscriptionCancelledEvent;
import com.floweytech.agrotrack.organization.shared.events.SubscriptionCreatedEvent;
import com.floweytech.agrotrack.organization.shared.events.SubscriptionExpiredEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionEventHandler {

    private final OrganizationRepository organizationRepository;

    public SubscriptionEventHandler(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @EventListener
    public void on(SubscriptionCreatedEvent event) {
        var command = new CreateOrganizationCommand(
            event.organizationName(),
            event.maxPlots(),
            event.ownerProfileId(),
            event.subscriptionId()
        );

        var organization = new Organization(command);
        organizationRepository.save(organization);
    }

    @EventListener
    public void on(SubscriptionActivatedEvent event) {
        var subscriptionId = new SubscriptionId(event.subscriptionId());
        organizationRepository.findBySubscriptionId(subscriptionId)
            .ifPresent(organization -> {
                organization.activate();
                organizationRepository.save(organization);
            });
    }

    @EventListener
    public void on(SubscriptionExpiredEvent event) {
        var subscriptionId = new SubscriptionId(event.subscriptionId());
        organizationRepository.findBySubscriptionId(subscriptionId)
            .ifPresent(organization -> {
                organization.deactivate();
                organizationRepository.save(organization);
            });
    }

    @EventListener
    public void on(SubscriptionCancelledEvent event) {
        var subscriptionId = new SubscriptionId(event.subscriptionId());
        organizationRepository.findBySubscriptionId(subscriptionId)
            .ifPresent(organization -> {
                organization.deactivate();
                organizationRepository.save(organization);
            });
    }
}
