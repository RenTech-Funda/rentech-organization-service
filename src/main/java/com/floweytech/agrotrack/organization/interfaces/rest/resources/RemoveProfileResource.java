package com.floweytech.agrotrack.organization.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RemoveProfileResource(
    @NotNull(message = "Profile ID is required")
    @Positive(message = "Profile ID must be positive")
    Long profileId
) {
}

