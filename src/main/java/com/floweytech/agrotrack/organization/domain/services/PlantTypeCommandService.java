package com.floweytech.agrotrack.organization.domain.services;

import com.floweytech.agrotrack.organization.domain.model.commands.CreatePlantTypeCommand;
public interface PlantTypeCommandService {
    Long handle(CreatePlantTypeCommand command);
}
