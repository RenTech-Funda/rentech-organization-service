package com.floweytech.agrotrack.organization.interfaces.rest.transform;

import com.floweytech.agrotrack.organization.domain.model.commands.AddUserToOrganizationCommand;
import com.floweytech.agrotrack.organization.interfaces.rest.resources.AddUserResource;

public class AddUserCommandFromResourceAssembler {

    public static AddUserToOrganizationCommand toCommandFromResource(Long organizationId, AddUserResource resource) {
        return new AddUserToOrganizationCommand(organizationId, resource.userId());
    }
}
