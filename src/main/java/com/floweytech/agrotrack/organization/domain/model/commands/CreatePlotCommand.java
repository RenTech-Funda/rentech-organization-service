package com.floweytech.agrotrack.organization.domain.model.commands;

import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlantTypeId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SizeArea;

public record CreatePlotCommand(
    String plotName,
    SizeArea sizeArea,
    PlantTypeId plantTypeId,
    String location,
    OrganizationId organizationId
) {}



