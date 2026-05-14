package com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories;

import com.floweytech.agrotrack.organization.domain.model.entities.PlantType;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlantTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlantTypeRepository extends JpaRepository<PlantType, Long> {
    boolean existsByName(String name);
    Optional<PlantType> findByPlantTypeId(PlantTypeId plantTypeId);
    Optional<PlantType> findByName(String name);
    List<PlantType> findAllByPredefinedTrue();
    List<PlantType> findAllByNameContainingIgnoreCase(String name);
}
