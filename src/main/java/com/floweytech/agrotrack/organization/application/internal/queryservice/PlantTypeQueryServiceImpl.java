package com.floweytech.agrotrack.organization.application.internal.queryservice;

import com.floweytech.agrotrack.organization.domain.model.entities.PlantType;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlantTypeId;
import com.floweytech.agrotrack.organization.domain.services.PlantTypeQueryService;
import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.PlantTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantTypeQueryServiceImpl implements PlantTypeQueryService {

    private final PlantTypeRepository plantTypeRepository;

    public PlantTypeQueryServiceImpl(PlantTypeRepository plantTypeRepository) {
        this.plantTypeRepository = plantTypeRepository;
    }

    @Override
    public Optional<PlantType> getByPlantTypeId(PlantTypeId plantTypeId) {
        return plantTypeRepository.findByPlantTypeId(plantTypeId);
    }

    @Override
    public Optional<PlantType> getByName(String name) {
        return plantTypeRepository.findByName(name);
    }

    @Override
    public List<PlantType> getAll() {
        return plantTypeRepository.findAll();
    }

    @Override
    public List<PlantType> getAllPredefined() {
        return plantTypeRepository.findAllByPredefinedTrue();
    }

    @Override
    public List<PlantType> searchByName(String name) {
        return plantTypeRepository.findAllByNameContainingIgnoreCase(name);
    }
}
