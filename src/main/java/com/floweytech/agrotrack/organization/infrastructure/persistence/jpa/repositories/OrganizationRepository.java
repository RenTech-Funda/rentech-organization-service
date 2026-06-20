package com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Organization;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SubscriptionId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Optional<Organization> findByOrganizationId(OrganizationId organizationId);
    boolean existsByOrganizationId(OrganizationId organizationId);
    Optional<Organization> findBySubscriptionId(SubscriptionId subscriptionId);
    Optional<Organization> findByOrganizationName(String organizationName);
    List<Organization> findAllByIsActiveTrue();
    List<Organization> findAllByOwnerUserId(UserId ownerUserId);
    List<Organization> findByUserIdsContaining(UserId userId);
}
