package com.floweytech.agrotrack.organization.shared.interfaces.acl;

import java.util.Optional;

/**
 * Profile Context Facade for ACL (Anti-Corruption Layer)
 * Exposes Profile bounded context services to other bounded contexts
 */
public interface ProfileContextFacade {
    /**
     * Get profile ID by user ID
     * @param userId The user ID
     * @return The profile ID if found, empty otherwise
     */
    Optional<Long> getProfileIdByUserId(Long userId);

    /**
     * Check if a profile exists by profile ID
     * @param profileId The profile ID
     * @return true if the profile exists, false otherwise
     */
    boolean existsByProfileId(Long profileId);
}

