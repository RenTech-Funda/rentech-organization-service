package com.floweytech.agrotrack.organization.domain.services;

import com.floweytech.agrotrack.organization.domain.model.commands.AddUserToOrganizationCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.RemoveUserFromOrganizationCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.UpdateOrganizationNameCommand;

public interface OrganizationCommandService {
    void handle(UpdateOrganizationNameCommand command);
    void handle(AddUserToOrganizationCommand command);
    void handle(RemoveUserFromOrganizationCommand command);
}
