package com.floweytech.agrotrack.organization.infrastructure.acl;

import com.floweytech.agrotrack.organization.shared.interfaces.acl.ProfileContextFacade;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProfileContextFacadeImpl implements ProfileContextFacade {
    @Override
    public boolean existsByProfileId(Long profileId) {
        return true;
    }
    @Override
    public Optional<Long> getProfileIdByUserId(Long userId) {
        return Optional.of(1L);
    }
}