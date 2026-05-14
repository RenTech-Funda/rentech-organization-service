package com.floweytech.agrotrack.organization.interfaces.rest.resources;

public record PlantTypeResource(
    Long id,
    Long plantTypeId,
    String plantType,
    String name,
    String description,
    Boolean predefined
) {
}

