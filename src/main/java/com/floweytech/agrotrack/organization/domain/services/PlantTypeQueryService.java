package com.floweytech.agrotrack.organization.domain.services;

import com.floweytech.agrotrack.organization.domain.model.entities.PlantType;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlantTypeId;

import java.util.List;
import java.util.Optional;

public interface PlantTypeQueryService {
    Optional<PlantType> getByPlantTypeId(PlantTypeId plantTypeId);
    Optional<PlantType> getByName(String name);
    List<PlantType> getAll();
    List<PlantType> getAllPredefined();
    List<PlantType> searchByName(String name);
}
