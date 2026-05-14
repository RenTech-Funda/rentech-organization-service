package com.floweytech.agrotrack.organization.domain.model.queries;

public record GetOrganizationsByProfileIdQuery(Long profileId) {
    public GetOrganizationsByProfileIdQuery {
        if (profileId == null || profileId <= 0)
            throw new IllegalArgumentException("profileId cannot be null or less than or equal to zero");
    }
}

