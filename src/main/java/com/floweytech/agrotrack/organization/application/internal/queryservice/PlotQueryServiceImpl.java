package com.floweytech.agrotrack.organization.application.internal.queryservice;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Plot;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlotId;
import com.floweytech.agrotrack.organization.domain.services.PlotQueryService;
import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.PlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlotQueryServiceImpl implements PlotQueryService {

    private final PlotRepository plotRepository;

    public PlotQueryServiceImpl(PlotRepository plotRepository) {
        this.plotRepository = plotRepository;
    }

    @Override
    public Optional<Plot> getById(Long id) {
        var plotId = new PlotId(id);
        return plotRepository.findByPlotId(plotId);
    }

    @Override
    public Optional<Plot> getByPlotId(String plotId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Plot> getByOrganizationId(OrganizationId organizationId) {
        return plotRepository.findAllByOrganizationId(organizationId);
    }

    @Override
    public List<Plot> getByPlotName(String plotName) {
        return plotRepository.findAllByPlotName(plotName);
    }

    @Override
    public List<Plot> getAll() {
        return plotRepository.findAll();
    }
}

