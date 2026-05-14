package com.floweytech.agrotrack.organization.interfaces.rest.transform;

import com.floweytech.agrotrack.organization.domain.model.commands.CreatePlotCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlantTypeId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SizeArea;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.CreatePlotResource;

public class CreatePlotCommandFromResourceAssembler {

    public static CreatePlotCommand toCommandFromResource(CreatePlotResource resource) {
        return new CreatePlotCommand(
            resource.plotName(),
            new SizeArea(resource.size(), resource.unit()),
            new PlantTypeId(resource.plantTypeId()),
            resource.location(),
            new OrganizationId(resource.organizationId())
        );
    }
}



