package com.floweytech.agrotrack.organization.domain.services;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Organization;
import com.floweytech.agrotrack.organization.domain.model.queries.GetOrganizationsByUserIdQuery;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SubscriptionId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.UserId;

import java.util.List;
import java.util.Optional;

public interface OrganizationQueryService {
    Optional<Organization> getByOrganizationId(OrganizationId organizationId);
    List<Organization> getByOwnerUserId(UserId ownerUserId);
    Optional<Organization> getByOrganizationName(String organizationName);
    Optional<Organization> getBySubscriptionId(SubscriptionId subscriptionId);
    List<Organization> handle(GetOrganizationsByUserIdQuery query);
}
