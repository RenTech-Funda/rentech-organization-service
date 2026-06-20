package com.floweytech.agrotrack.organization.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RemoveUserResource(
    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be positive")
    Long userId
) {
}
