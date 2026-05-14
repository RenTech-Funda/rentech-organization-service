package com.floweytech.agrotrack.organization.domain.model.commands;

public record UpdateOrganizationNameCommand(
    Long organizationId,
    String organizationName
) {
}

