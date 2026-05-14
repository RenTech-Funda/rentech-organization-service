package com.floweytech.agrotrack.organization.infrastructure.persistence.jpa.repositories;

import com.floweytech.agrotrack.organization.domain.model.aggregate.Organization;
import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.ProfileId;
import com.floweytech.agrotrack.organization.domain.model.valueobject.SubscriptionId;
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
    List<Organization> findAllByOwnerProfileId(ProfileId ownerProfileId);
    List<Organization> findByProfileIdsContaining(ProfileId profileId);
}
