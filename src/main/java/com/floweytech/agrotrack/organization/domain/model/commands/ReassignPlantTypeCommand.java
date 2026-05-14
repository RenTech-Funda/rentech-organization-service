package com.floweytech.agrotrack.organization.domain.model.commands;

import com.floweytech.agrotrack.organization.domain.model.valueobject.PlantTypeId;

public record ReassignPlantTypeCommand(
    Long plotId,
    PlantTypeId plantTypeId
) {
}

