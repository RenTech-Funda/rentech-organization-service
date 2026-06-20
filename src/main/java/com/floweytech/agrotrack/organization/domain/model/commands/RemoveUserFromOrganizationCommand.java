package com.floweytech.agrotrack.organization.domain.model.commands;

public record RemoveUserFromOrganizationCommand(
    Long organizationId,
    Long userId
) {
}
