package com.example.infoplus.profile.repository;

import com.example.infoplus.profile.domain.Profile;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@ActiveProfiles("test")
class ProfileRepositoryTest {

    Profile profile;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        profile = Profile.of("KIM");
    }

    @Test
    void testSaveAndFindProfile() {
        profileRepository.save(profile);

        em.flush();
        em.clear();

        Profile findProfile = profileRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        assertThat(profile).isEqualTo(findProfile);
    }

    @Test
    void testFindProfile_notFoundError() {
        assertThatThrownBy(() -> profileRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Profile not found")));
    }
}