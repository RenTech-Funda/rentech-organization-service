package com.floweytech.agrotrack.organization.domain.model.valueobject;

public record SubscriptionId(Long value) {
    public SubscriptionId() {
        this(null);
    }
    public SubscriptionId {
        if (value == null || value < 1)
            throw new IllegalArgumentException("SubscriptionId must be greater than zero");
    }
}
