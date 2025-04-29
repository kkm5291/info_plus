package com.example.infoplus.domain.member.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.infoplus.domain.member.entity.Member.INVALID_AMOUNT_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    Member member;

    @BeforeEach
    void setUp() {
        member = Member.of("KIM");
    }

    @Test
    void convertAmountToPoint() {
        Long amount = 1000L;

        member.convertAmountToPoint(amount);
        assertThat(member.getPoint()).isEqualTo(amount);
    }

    @Test
    void convertAmountToPoint_invalidAmount() {
        Long amount = -1000L;

        assertThatThrownBy(() -> member.convertAmountToPoint(amount))
                .hasMessage(INVALID_AMOUNT_ERROR)
                .isInstanceOf(IllegalArgumentException.class);
    }
}