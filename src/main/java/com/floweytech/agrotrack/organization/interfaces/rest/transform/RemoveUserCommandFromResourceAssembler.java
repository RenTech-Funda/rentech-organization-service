package com.floweytech.agrotrack.organization.interfaces.rest.transform;

import com.floweytech.agrotrack.organization.domain.model.commands.RemoveUserFromOrganizationCommand;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.RemoveUserResource;

public class RemoveUserCommandFromResourceAssembler {

    public static RemoveUserFromOrganizationCommand toCommandFromResource(Long organizationId, RemoveUserResource resource) {
        return new RemoveUserFromOrganizationCommand(organizationId, resource.userId());
    }
}
