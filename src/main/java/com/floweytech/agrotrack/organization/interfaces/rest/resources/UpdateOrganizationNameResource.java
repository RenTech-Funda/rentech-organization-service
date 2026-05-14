package com.floweytech.agrotrack.organization.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrganizationNameResource(
        @NotBlank(message = "Organization name is required")
        String organizationName
) {
}