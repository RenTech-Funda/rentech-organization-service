package com.floweytech.agrotrack.organization.interfaces.rest.transform;

import com.floweytech.agrotrack.organization.domain.model.commands.ReassignPlantTypeCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlantTypeId;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.ReassignPlantTypeResource;

public class ReassignPlantTypeCommandFromResourceAssembler {

    public static ReassignPlantTypeCommand toCommandFromResource(Long plotId, ReassignPlantTypeResource resource) {
        return new ReassignPlantTypeCommand(
            plotId,
            new PlantTypeId(resource.plantTypeId())
        );
    }
}

