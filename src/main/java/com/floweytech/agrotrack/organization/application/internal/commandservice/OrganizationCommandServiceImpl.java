package com.floweytech.agrotrack.organization.application.internal.commandservice;

import com.floweytech.agrotrack.organization.domain.model.commands.AddUserToOrganizationCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.RemoveUserFromOrganizationCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.UpdateOrganizationNameCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.UserId;
import com.floweytech.agrotrack.organization.domain.services.OrganizationCommandService;
import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import com.floweytech.agrotrack.organization.shared.infrastructure.security.AuthenticatedUserProvider;
import org.springframework.stereotype.Service;

@Service
public class OrganizationCommandServiceImpl implements OrganizationCommandService {

    private final OrganizationRepository organizationRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public OrganizationCommandServiceImpl(OrganizationRepository organizationRepository,
                                          AuthenticatedUserProvider authenticatedUserProvider) {
        this.organizationRepository = organizationRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @Override
    public void handle(UpdateOrganizationNameCommand command) {
        var organizationId = new OrganizationId(command.organizationId());

        var organization = organizationRepository.findByOrganizationId(organizationId)
            .orElseThrow(() -> new IllegalArgumentException("Organization with id " + command.organizationId() + " not found"));

        validateOwnership(organization);

        organization.setOrganizationName(command.organizationName());
        organizationRepository.save(organization);
    }

    @Override
    public void handle(AddUserToOrganizationCommand command) {
        var organizationId = new OrganizationId(command.organizationId());

        var organization = organizationRepository.findByOrganizationId(organizationId)
            .orElseThrow(() -> new IllegalArgumentException("Organization with id " + command.organizationId() + " not found"));

        validateOwnership(organization);

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

        validateOwnership(organization);

        var userId = new UserId(command.userId());
        organization.removeUser(userId);
        organizationRepository.save(organization);
    }

    private void validateOwnership(com.floweytech.agrotrack.organization.domain.model.aggregate.Organization organization) {
        var isOwner = organization.getOwnerUserId().value().equals(authenticatedUserProvider.getUserId());
        if (!isOwner && !authenticatedUserProvider.isAdministrator()) {
            throw new org.springframework.security.access.AccessDeniedException(
                    "Only the organization owner or an administrator can modify it");
        }
    }
}
