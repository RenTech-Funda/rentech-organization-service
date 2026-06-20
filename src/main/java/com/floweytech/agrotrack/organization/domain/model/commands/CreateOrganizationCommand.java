package com.floweytech.agrotrack.organization.domain.model.commands;

public record CreateOrganizationCommand(
    String organizationName,
    Integer maxPlots,
    Long ownerUserId,
    Long subscriptionId
) {
}
