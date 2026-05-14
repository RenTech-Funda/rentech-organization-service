package com.floweytech.agrotrack.organization.domain.model.commands;

public record RemoveProfileFromOrganizationCommand(
    Long organizationId,
    Long profileId
) {
}

