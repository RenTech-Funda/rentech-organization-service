package com.floweytech.agrotrack.organization.interfaces.rest.resources;

import java.util.List;

public record OrganizationResource(
    Long id,
    Long organizationId,
    String organizationName,
    Boolean isActive,
    Integer maxPlots,
    Long ownerProfileId,
    List<Long> profileIds,
    Long subscriptionId
) {
}


