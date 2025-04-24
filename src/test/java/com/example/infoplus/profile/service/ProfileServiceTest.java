package com.example.infoplus.profile.service;

import com.example.infoplus.profile.domain.Profile;
import com.example.infoplus.profile.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;
    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        profileService = new ProfileServiceImpl(profileRepository);
    }

    @Test
    void getProfileById() {
        BDDMockito.given(profileRepository.findById(1L)).willReturn(Optional.of(Profile.of("KIM")));

        Profile profile = profileService.getProfileById(1L);
        assertNotNull(profile);
    }
}