package com.floweytech.agrotrack.organization.interfaces.rest.transform;

import com.floweytech.agrotrack.organization.domain.model.commands.UpdateOrganizationNameCommand;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.UpdateOrganizationNameResource;

public class UpdateOrganizationNameCommandFromResourceAssembler {

    public static UpdateOrganizationNameCommand toCommandFromResource(Long organizationId, UpdateOrganizationNameResource resource) {
        return new UpdateOrganizationNameCommand(
            organizationId,
            resource.organizationName()
        );
    }
}

