package com.floweytech.agrotrack.organization.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReassignPlantTypeResource(
    @NotNull(message = "Plant type ID is required")
    @Positive(message = "Plant type ID must be positive")
    Long plantTypeId
) {
}

