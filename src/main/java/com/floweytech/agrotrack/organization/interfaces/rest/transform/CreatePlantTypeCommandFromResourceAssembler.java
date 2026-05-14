package com.floweytech.agrotrack.organization.interfaces.rest.transform;

import com.floweytech.agrotrack.organization.domain.model.commands.CreatePlantTypeCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlantTypes;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.CreatePlantTypeResource;

public class CreatePlantTypeCommandFromResourceAssembler {

    public static CreatePlantTypeCommand toCommandFromResource(CreatePlantTypeResource resource) {
        return new CreatePlantTypeCommand(
            null, // plantTypeId se genera automáticamente después del persist
            PlantTypes.valueOf(resource.plantType().toUpperCase()),
            resource.name(),
            resource.description()
        );
    }
}

