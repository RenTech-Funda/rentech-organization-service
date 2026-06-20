package com.floweytech.agrotrack.organization.application.internal.commandservice;

import com.floweytech.agrotrack.organization.domain.model.commands.CreatePlantTypeCommand;
import com.floweytech.agrotrack.organization.domain.model.entities.PlantType;
import com.floweytech.agrotrack.organization.domain.services.PlantTypeCommandService;
import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.PlantTypeRepository;
import com.floweytech.agrotrack.organization.shared.infrastructure.security.AuthenticatedUserProvider;
import org.springframework.stereotype.Service;

@Service
public class PlantTypeCommandServiceImpl implements PlantTypeCommandService {
    private final PlantTypeRepository plantTypeRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public PlantTypeCommandServiceImpl(PlantTypeRepository plantTypeRepository,
                                       AuthenticatedUserProvider authenticatedUserProvider) {
        this.plantTypeRepository = plantTypeRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Override
    public Long handle(CreatePlantTypeCommand command) {
        if (!authenticatedUserProvider.isAdministrator()) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "Only administrators can create plant types");
        }
        // Validar que no exista un PlantType con el mismo nombre
        if (plantTypeRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("A PlantType with name '" + command.name() + "' already exists");
        }

        // Crear la entidad PlantType
        var plantType = new PlantType(command);

        // Guardar en la base de datos
        var savedPlantType = plantTypeRepository.save(plantType);

        // Retornar el ID generado
        return savedPlantType.getId();
    }
}
