package com.floweytech.agrotrack.organization.application.internal.commandservice;

import com.floweytech.agrotrack.organization.domain.model.commands.AddUserToOrganizationCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.RemoveUserFromOrganizationCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.UpdateOrganizationNameCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.UserId;
import com.floweytech.agrotrack.organization.domain.services.OrganizationCommandService;
import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import org.springframework.stereotype.Service;

@Service
public class OrganizationCommandServiceImpl implements OrganizationCommandService {

    private final OrganizationRepository organizationRepository;

    public OrganizationCommandServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void handle(UpdateOrganizationNameCommand command) {
        var organizationId = new OrganizationId(command.organizationId());

        var organization = organizationRepository.findByOrganizationId(organizationId)
            .orElseThrow(() -> new IllegalArgumentException("Organization with id " + command.organizationId() + " not found"));

        organization.setOrganizationName(command.organizationName());
        organizationRepository.save(organization);
    }

    @Override
    public void handle(AddUserToOrganizationCommand command) {
        var organizationId = new OrganizationId(command.organizationId());

        var organization = organizationRepository.findByOrganizationId(organizationId)
            .orElseThrow(() -> new IllegalArgumentException("Organization with id " + command.organizationId() + " not found"));

        var userId = new UserId(command.userId());

        if (organization.getUserIds().contains(userId)) {
            throw new IllegalArgumentException("User with id " + command.userId() + " is already in the organization");
        }

        organization.addUser(userId);
        organizationRepository.save(organization);
    }

    @Override
    public void handle(RemoveUserFromOrganizationCommand command) {
        var organizationId = new OrganizationId(command.organizationId());

        var organization = organizationRepository.findByOrganizationId(organizationId)
            .orElseThrow(() -> new IllegalArgumentException("Organization with id " + command.organizationId() + " not found"));

        var userId = new UserId(command.userId());
        organization.removeUser(userId);
        organizationRepository.save(organization);
    }
}
