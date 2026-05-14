package com.floweytech.agrotrack.organization.domain.services;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Plot;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;

import java.util.List;
import java.util.Optional;

public interface PlotQueryService {
    Optional<Plot> getById(Long id);
    Optional<Plot> getByPlotId(String plotId);
    List<Plot> getByOrganizationId(OrganizationId organizationId);
    List<Plot> getByPlotName(String plotName);
    List<Plot> getAll();
}
