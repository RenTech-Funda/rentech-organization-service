package com.floweytech.agrotrack.organization.domain.model.valueobject;

import jakarta.persistence.Embeddable;

@Embeddable
public record PlotId(Long value) {
    public PlotId() {
        this(null);
    }

    public PlotId {
        if (value == null || value < 1)
            throw new IllegalArgumentException("PlotId must be greater than zero");
    }
}
