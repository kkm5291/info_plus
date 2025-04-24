package com.example.infoplus.profile.service;

import com.example.infoplus.profile.domain.Profile;
import com.example.infoplus.profile.repository.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile getProfile() {
        return null;
    }
}
