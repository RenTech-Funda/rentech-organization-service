package com.floweytech.agrotrack.organization.interfaces.rest.transform;

import com.floweytech.agrotrack.organization.domain.model.entities.PlantType;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.PlantTypeResource;

public class PlantTypeResourceFromEntityAssembler {

    public static PlantTypeResource toResourceFromEntity(PlantType entity) {
        return new PlantTypeResource(
            entity.getId(),
            entity.getPlantTypeId() != null ? entity.getPlantTypeId().value() : null,
            entity.getPlantTypes() != null ? entity.getPlantTypes().name() : null,
            entity.getName(),
            entity.getDescription(),
            entity.getPredefined()
        );
    }
}

