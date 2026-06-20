package com.floweytech.agrotrack.organization.domain.model.commands;

public record AddUserToOrganizationCommand(
    Long organizationId,
    Long userId
) {
}
