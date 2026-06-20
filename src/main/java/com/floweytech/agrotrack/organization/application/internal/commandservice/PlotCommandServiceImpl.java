package com.floweytech.agrotrack.organization.application.internal.commandservice;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Plot;
import com.floweytech.agrotrack.organization.domain.model.valueobject.PlotId;
import com.floweytech.agrotrack.organization.domain.model.commands.CreatePlotCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.ReassignPlantTypeCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.ReassignSizeAreaCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.UserId;
import com.floweytech.agrotrack.organization.domain.services.PlotCommandService;
import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.PlotRepository;
import com.floweytech.agrotrack.organization.shared.interfaces.acl.TokenContextFacade;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class PlotCommandServiceImpl implements PlotCommandService {

    private final PlotRepository plotRepository;
    private final OrganizationRepository organizationRepository;
    private final TokenContextFacade tokenContextFacade;

    public PlotCommandServiceImpl(PlotRepository plotRepository,
                                   OrganizationRepository organizationRepository,
                                   TokenContextFacade tokenContextFacade) {
        this.plotRepository = plotRepository;
        this.organizationRepository = organizationRepository;
        this.tokenContextFacade = tokenContextFacade;
    }

    /**
     * Validates that the organization exists, is active, and the user is the owner
     */
    private void validateOrganizationOwnership(Long organizationIdValue, HttpServletRequest request) {
        var organization = organizationRepository.findByOrganizationId(
                new com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId(organizationIdValue))
                .orElseThrow(() -> new IllegalArgumentException(
                        "Organization with id " + organizationIdValue + " not found"));

        if (!organization.getIsActive()) {
            throw new IllegalArgumentException(
                    "Organization with id " + organizationIdValue + " is not active");
        }

        Long userId = tokenContextFacade.extractUserIdFromToken(request);

        if (!organization.getOwnerUserId().equals(new UserId(userId))) {
            throw new IllegalArgumentException(
                    "User is not the owner of organization " + organizationIdValue);
        }
    }

    @Override
    public Long handle(CreatePlotCommand command, HttpServletRequest request) {
        validateOrganizationOwnership(command.organizationId().value(), request);

        var plot = new Plot(command);
        var savedPlot = plotRepository.saveAndFlush(plot);

        return savedPlot.getPlotId().value();
    }

    @Override
    public void handle(ReassignPlantTypeCommand command, HttpServletRequest request) {
        var plotId = new PlotId(command.plotId());

        var plot = plotRepository.findByPlotId(plotId)
            .orElseThrow(() -> new IllegalArgumentException("Plot with id " + command.plotId() + " not found"));

        validateOrganizationOwnership(plot.getOrganizationId().value(), request);

        plot.reassignPlantType(command.plantTypeId());
        plotRepository.save(plot);
    }

    @Override
    public void handle(ReassignSizeAreaCommand command, HttpServletRequest request) {
        var plotId = new PlotId(command.plotId());

        var plot = plotRepository.findByPlotId(plotId)
            .orElseThrow(() -> new IllegalArgumentException("Plot with id " + command.plotId() + " not found"));

        validateOrganizationOwnership(plot.getOrganizationId().value(), request);

        plot.reassignSizeArea(command.sizeArea());
        plotRepository.save(plot);
    }
}
