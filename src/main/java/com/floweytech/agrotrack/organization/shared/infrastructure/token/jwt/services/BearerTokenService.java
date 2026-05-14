package com.floweytech.agrotrack.organization.shared.infrastructure.token.jwt.services;

import jakarta.servlet.http.HttpServletRequest;

public interface BearerTokenService {
    String generateToken(String username, String role, Long userId);
    String getUsernameFromToken(String token);
    Long getUserIdFromToken(String token);
    String getRoleFromToken(String token);
    boolean validateToken(String token);
    String getBearerTokenFrom(HttpServletRequest request);
}