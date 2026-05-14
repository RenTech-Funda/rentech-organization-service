package com.floweytech.agrotrack.organization.domain.model.events;

import com.floweytech.agrotrack.organization.domain.model.valueobject.OrganizationId;

public record OrganizationDeactivatedEvent(OrganizationId organizationId) {}

