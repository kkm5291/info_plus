package com.example.infoplus.domain.profile.repository;

import com.example.infoplus.domain.member.Member;
import com.example.infoplus.domain.member.repository.MemberRepository;
import com.example.infoplus.domain.profile.Profile;
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

    Member member;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        member = Member.of("KIM");
    }

    @Test
    void testSaveAndFindProfile() {
        memberRepository.save(member);

        em.flush();
        em.clear();

        Profile findProfile = profileRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        Long profileId = member.getProfile().getId();
        assertThat(findProfile.getId()).isEqualTo(profileId);

    }

    @Test
    void testFindProfile_notFoundError() {
        assertThatThrownBy(() -> profileRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException("Profile not found")));
    }
}