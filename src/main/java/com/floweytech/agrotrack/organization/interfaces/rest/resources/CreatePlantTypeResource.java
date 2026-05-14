package com.floweytech.agrotrack.organization.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePlantTypeResource(
    @NotNull(message = "Plant type is required")
    String plantType,

    @NotBlank(message = "Name is required")
    String name,

    String description
    ) {
}

