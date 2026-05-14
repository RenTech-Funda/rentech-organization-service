package com.floweytech.agrotrack.organization.interfaces.rest.resources;

public record PlotResource(
    Long id,
    Long plotId,
    String plotName,
    Double size,
    String unit,
    Long plantTypeId,
    String location,
    Long organizationId
) {
}

