package com.floweytech.agrotrack.organization.domain.model.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public record OrganizationId(Long value) {
    public OrganizationId {
        if (value != null && value < 1)
            throw new IllegalArgumentException("OrganizationId must be greater than zero");
    }

    public OrganizationId() {
        this(null);
    }
}
