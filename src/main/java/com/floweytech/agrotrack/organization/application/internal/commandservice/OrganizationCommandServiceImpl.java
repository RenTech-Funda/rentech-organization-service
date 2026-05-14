package com.floweytech.agrotrack.organization.application.internal.commandservice;

import com.floweytech.agrotrack.organization.domain.model.commands.AddProfileToOrganizationCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.RemoveProfileFromOrganizationCommand;
import com.floweytech.agrotrack.organization.domain.model.commands.UpdateOrganizationNameCommand;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.ProfileId;
import com.floweytech.agrotrack.organization.domain.services.OrganizationCommandService;
import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import com.floweytech.agrotrack.organization.shared.interfaces.acl.ProfileContextFacade;
import org.springframework.stereotype.Service;

@Service
public class OrganizationCommandServiceImpl implements OrganizationCommandService {

    private final OrganizationRepository organizationRepository;
    private final ProfileContextFacade profileContextFacade;

    public OrganizationCommandServiceImpl(OrganizationRepository organizationRepository,
                                           ProfileContextFacade profileContextFacade) {
        this.organizationRepository = organizationRepository;
        this.profileContextFacade = profileContextFacade;
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
    public void handle(AddProfileToOrganizationCommand command) {
        var organizationId = new OrganizationId(command.organizationId());

        var organization = organizationRepository.findByOrganizationId(organizationId)
            .orElseThrow(() -> new IllegalArgumentException("Organization with id " + command.organizationId() + " not found"));

        if (!profileContextFacade.existsByProfileId(command.profileId())) {
            throw new IllegalArgumentException("Profile with id " + command.profileId() + " not found");
        }

        var profileId = new ProfileId(command.profileId());

        if (organization.getProfileIds().contains(profileId)) {
            throw new IllegalArgumentException("Profile with id " + command.profileId() + " is already in the organization");
        }

        organization.addProfile(profileId);
        organizationRepository.save(organization);
    }

    @Override
    public void handle(RemoveProfileFromOrganizationCommand command) {
        var organizationId = new OrganizationId(command.organizationId());

        var organization = organizationRepository.findByOrganizationId(organizationId)
            .orElseThrow(() -> new IllegalArgumentException("Organization with id " + command.organizationId() + " not found"));

        var profileId = new ProfileId(command.profileId());
        organization.removeProfile(profileId);
        organizationRepository.save(organization);
    }
}
