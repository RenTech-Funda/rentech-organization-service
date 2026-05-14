package com.floweytech.agrotrack.organization.domain.model.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public record PlantTypeId(
    Long value
) {
    public PlantTypeId() {
        this(null);
    }
    public PlantTypeId {
        if (value == null || value < 1)
            throw new IllegalArgumentException("PlantTypeId must be greater than zero");
    }
}
