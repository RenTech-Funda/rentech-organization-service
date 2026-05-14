package com.floweytech.agrotrack.organization.domain.model.commands;

public record AddProfileToOrganizationCommand(
    Long organizationId,
    Long profileId
) {
}

