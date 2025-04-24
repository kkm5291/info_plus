package com.example.infoplus.repository;

import com.example.infoplus.config.QueryDSLConfig;
import com.example.infoplus.domain.Member;
import com.example.infoplus.domain.Profile;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Import({ProfileQueryRepository.class, QueryDSLConfig.class})
class ProfileQueryRepositoryTest {

    @Autowired
    ProfileQueryRepository profileQueryRepository;

    @Autowired
    EntityManager em;

    @BeforeEach
    void setUp() {
        Member kim = Member.of("KIM");
        Member lee = Member.of("LEE");
        Member gong = Member.of("GONG");
        em.persist(kim);
        em.persist(lee);
        em.persist(gong);

        em.flush();
        em.clear();
    }

    @Test
    void findAllProfilesWithSort() {
        List<Profile> allProfiles = profileQueryRepository.findAllProfilesWithSort("desc");
        assertThat(allProfiles.size()).isEqualTo(3);
    }
}