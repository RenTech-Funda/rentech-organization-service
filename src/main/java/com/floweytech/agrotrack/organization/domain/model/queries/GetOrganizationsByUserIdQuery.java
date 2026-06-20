package com.floweytech.agrotrack.organization.domain.model.queries;

public record GetOrganizationsByUserIdQuery(Long userId) {
    public GetOrganizationsByUserIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId must be a positive non-null value");
        }
    }
}
