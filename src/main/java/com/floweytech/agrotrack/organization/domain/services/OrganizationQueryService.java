package com.floweytech.agrotrack.organization.domain.services;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Organization;
import com.floweytech.agrotrack.organization.domain.model.queries.GetOrganizationsByProfileIdQuery;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.ProfileId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SubscriptionId;

import java.util.List;
import java.util.Optional;

public interface OrganizationQueryService {
    Optional<Organization> getByOrganizationId(OrganizationId organizationId);
    List<Organization> getByOwnerProfileId(ProfileId ownerProfileId);
    Optional<Organization> getByOrganizationName(String organizationName);
    Optional<Organization> getBySubscriptionId(SubscriptionId subscriptionId);
    List<Organization> handle(GetOrganizationsByProfileIdQuery query);
}
