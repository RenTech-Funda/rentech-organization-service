package com.floweytech.agrotrack.organization.application.internal.queryservice;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Organization;
import com.floweytech.agrotrack.organization.domain.model.queries.GetOrganizationsByProfileIdQuery;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.ProfileId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SubscriptionId;
import com.floweytech.agrotrack.organization.domain.services.OrganizationQueryService;
import com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories.OrganizationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationQueryServiceImpl implements OrganizationQueryService {

    private final OrganizationRepository organizationRepository;

    public OrganizationQueryServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Optional<Organization> getByOrganizationId(OrganizationId organizationId) {
        return organizationRepository.findByOrganizationId(organizationId);
    }

    @Override
    public List<Organization> getByOwnerProfileId(ProfileId ownerProfileId) {
        return organizationRepository.findAllByOwnerProfileId(ownerProfileId);
    }

    @Override
    public Optional<Organization> getByOrganizationName(String organizationName) {
        return organizationRepository.findByOrganizationName(organizationName);
    }

    @Override
    public Optional<Organization> getBySubscriptionId(SubscriptionId subscriptionId) {
        return organizationRepository.findBySubscriptionId(subscriptionId);
    }

    @Override
    public List<Organization> handle(GetOrganizationsByProfileIdQuery query) {
        var profileId = new ProfileId(query.profileId());
        return organizationRepository.findByProfileIdsContaining(profileId);
    }
}
