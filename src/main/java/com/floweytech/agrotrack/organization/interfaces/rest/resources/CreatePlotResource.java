package com.floweytech.agrotrack.organization.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePlotResource(

    @NotBlank(message = "Plot name is required")
    String plotName,

    @NotNull(message = "Size is required")
    @Positive(message = "Size must be positive")
    Double size,

    @NotBlank(message = "Unit is required")
    String unit,

    @NotNull(message = "Plant type ID is required")
    @Positive(message = "Plant type ID must be positive")
    Long plantTypeId,

    @NotBlank(message = "Location is required")
    String location,

    @NotNull(message = "Organization ID is required")
    @Positive(message = "Organization ID must be positive")
    Long organizationId
) {
}

