package com.example.infoplus.domain.profile;

import com.example.infoplus.domain.member.entity.Member;
import com.example.infoplus.domain.profile.entity.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    Profile profile;

    @BeforeEach
    void setUp() {
        profile = Member.of("KIM").getProfile();
    }

    @Test
    void increaseViewCount() {
        profile.increaseViewCount();
        assertEquals(1, profile.getViewCount());
    }
}