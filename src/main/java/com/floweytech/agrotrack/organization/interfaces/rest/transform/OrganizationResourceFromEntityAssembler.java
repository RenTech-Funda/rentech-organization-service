package com.floweytech.agrotrack.organization.interfaces.rest.transform;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Organization;
import com.floweytech.agrotrack.organization.domain.model.valueobject.ProfileId;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.OrganizationResource;

public class OrganizationResourceFromEntityAssembler {

    public static OrganizationResource toResourceFromEntity(Organization entity) {
        return new OrganizationResource(
            entity.getId(),
            entity.getOrganizationId().value(),
            entity.getOrganizationName(),
            entity.getIsActive(),
            entity.getMaxPlots(),
            entity.getOwnerProfileId().value(),
            entity.getProfileIds().stream()
                .map(ProfileId::value)
                .toList(),
            entity.getSubscriptionId().value()
        );
    }
}

