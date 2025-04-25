package com.example.infoplus.repository;

import com.example.infoplus.config.QueryDSLConfig;
import com.example.infoplus.domain.member.Member;
import com.example.infoplus.domain.profile.Profile;
import com.example.infoplus.domain.profile.ProfileSortType;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@DataJpaTest
@ActiveProfiles("test")
@Import({ProfileQueryRepository.class, QueryDSLConfig.class})
class ProfileQueryRepositoryTest {

    Member kim;
    Member lee;
    Member gong;

    @Autowired
    ProfileQueryRepository profileQueryRepository;

    @Autowired
    EntityManager em;

    @MockitoBean
    ProfileRepository profileRepository;

    @BeforeEach
    void setUp() throws InterruptedException {
        kim = Member.of("김");
        lee = Member.of("이");
        gong = Member.of("공");

        em.persist(kim);
        Thread.sleep(100);

        em.persist(lee);
        Thread.sleep(100);

        em.persist(gong);

        em.flush();
        em.clear();
    }

    @Test
    void findAllProfilesWithSort_nameASC() {
        List<Profile> result = profileQueryRepository.findAllProfilesWithSort(ProfileSortType.NAME_ASC);

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getMember().getName()).isEqualTo("공");
        assertThat(result.get(1).getMember().getName()).isEqualTo("김");
        assertThat(result.get(2).getMember().getName()).isEqualTo("이");
    }

    @Test
    void findAllProfilesWithSort_dateCreatedDESC() {
        List<Profile> result = profileQueryRepository.findAllProfilesWithSort(ProfileSortType.DATE_CREATED_DESC);

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getMember().getName()).isEqualTo("공");
        assertThat(result.get(1).getMember().getName()).isEqualTo("이");
        assertThat(result.get(2).getMember().getName()).isEqualTo("김");
    }

    @Test
    void findAllProfilesWithSort_viewCountDESC() {
        given(profileRepository.findById(0L)).willReturn(java.util.Optional.of(kim.getProfile()));
        given(profileRepository.findById(1L)).willReturn(java.util.Optional.of(lee.getProfile()));
        given(profileRepository.findById(2L)).willReturn(java.util.Optional.of(gong.getProfile()));

        Profile findKimProfile = profileRepository.findById(0L).orElse(null);
        Profile findLeeProfile = profileRepository.findById(1L).orElse(null);
        Profile findGongProfile = profileRepository.findById(2L).orElse(null);

        findKimProfile.increaseViewCount();
        findKimProfile.increaseViewCount();
        findKimProfile.increaseViewCount();

        findLeeProfile.increaseViewCount();

        em.flush();
        em.clear();

        List<Profile> result = profileQueryRepository.findAllProfilesWithSort(ProfileSortType.VIEW_COUNT_DESC);

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getMember().getName()).isEqualTo("김");
        assertThat(result.get(1).getMember().getName()).isEqualTo("이");
        assertThat(result.get(2).getMember().getName()).isEqualTo("공");
    }

//    @Test
//    void findAllProfilesWithPagingAndSorting() {
//        Page<Profile> allProfilesWithPagingAndSorting = profileQueryRepository.findAllProfilesWithPagingAndSorting(ProfileSortType.NAME_ASC, PageRequest.of(0, 1));
//
//    }
}