package com.floweytech.agrotrack.organization.interfaces.rest.transform;

import com.floweytech.agrotrack.organization.domain.model.commands.AddProfileToOrganizationCommand;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.AddProfileResource;

public class AddProfileCommandFromResourceAssembler {

    public static AddProfileToOrganizationCommand toCommandFromResource(Long organizationId, AddProfileResource resource) {
        return new AddProfileToOrganizationCommand(
            organizationId,
            resource.profileId()
        );
    }
}

