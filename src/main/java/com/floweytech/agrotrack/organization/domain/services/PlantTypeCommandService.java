package com.floweytech.agrotrack.organization.domain.services;

import com.floweytech.agrotrack.organization.domain.model.commands.CreatePlantTypeCommand;
import jakarta.servlet.http.HttpServletRequest;

public interface PlantTypeCommandService {
    Long handle(CreatePlantTypeCommand command, HttpServletRequest request);
}
