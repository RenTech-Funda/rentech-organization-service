package com.floweytech.agrotrack.organization.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReassignSizeAreaResource(
    @NotNull(message = "Size is required")
    @Positive(message = "Size must be positive")
    Double size,

    @NotBlank(message = "Unit is required")
    String unit
) {
}

