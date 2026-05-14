package com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Plot;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlotId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlotRepository extends JpaRepository<Plot, Long> {
    Optional<Plot> findByPlotId(PlotId plotId);
    boolean existsByPlotId(PlotId plotId);
    List<Plot> findAllByOrganizationId(OrganizationId organizationId);
    List<Plot> findAllByPlotName(String plotName);
}
