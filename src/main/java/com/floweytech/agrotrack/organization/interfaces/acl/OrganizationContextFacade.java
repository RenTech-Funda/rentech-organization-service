package com.floweytech.agrotrack.organization.interfaces.acl;

/**
 * Organization Context Facade Interface
 * @summary
 * Defines the contract for the Anti-Corruption Layer (ACL) of the Organization Context.
 * It serves as a gateway for other Bounded Contexts to validate data or request information
 * related to organizations and plots, ensuring loose coupling and domain isolation.
 *
 * @author FloweyTech developer team
 */
public interface OrganizationContextFacade {
    /**
     * Check if a Plot exists
     * @summary
     * Verifies the existence of a specific plot within the organization context
     * using its unique identifier. This is commonly used by external services to validate
     * plot references before performing operations linked to them.
     *
     * @param plotId The unique identifier of the plot to check.
     * @return true if the plot exists, false otherwise.
     */
    boolean existsPlotById(Long plotId);
}
