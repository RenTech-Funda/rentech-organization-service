package com.floweytech.agrotrack.organization.shared.events;

public record SubscriptionCreatedEvent(
    Long subscriptionId,
    String organizationName,
    Integer maxPlots,
    Long ownerUserId
) {
}
