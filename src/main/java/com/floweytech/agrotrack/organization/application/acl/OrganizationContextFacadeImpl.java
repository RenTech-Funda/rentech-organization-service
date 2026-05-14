package com.floweytech.agrotrack.organization.application.acl;

import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.PlotRepository;
import com.floweytech.agrotrack.organization.interfaces.acl.OrganizationContextFacade;
import org.springframework.stereotype.Service;

/**
 * Organization Context Facade Implementation
 * @summary
 * Implements the contract defined by the OrganizationContextFacade.
 * This class acts as the concrete Anti-Corruption Layer (ACL) entry point,
 * bridging external requests to the internal domain logic or repositories
 * of the Organization Context.
 *
 * @author FloweyTech developer team
 */
@Service
public class OrganizationContextFacadeImpl implements OrganizationContextFacade {

    private final PlotRepository plotRepository;

    /**
     * Constructor
     * @summary
     * Initializes the facade implementation with the required PlotRepository.
     *
     * @param plotRepository The repository used to access plot data.
     */
    public OrganizationContextFacadeImpl(PlotRepository plotRepository) {
        this.plotRepository = plotRepository;
    }

    /**
     * Check if a Plot exists
     * @summary
     * Delegates the existence check directly to the PlotRepository.
     * This provides a lightweight, read-only verification mechanism for external contexts.
     *
     * @param plotId The unique identifier of the plot.
     * @return true if the plot is found in the database, false otherwise.
     */
    @Override
    public boolean existsPlotById(Long plotId) {
        return plotRepository.existsById(plotId);
    }
}