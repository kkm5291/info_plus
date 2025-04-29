package com.example.infoplus.domain.coupon.entity;

import com.example.infoplus.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.example.infoplus.domain.coupon.entity.Coupon.COUPON_INVALID_AMOUNT_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponTest {

    Member member;
    Coupon coupon;

    @BeforeEach
    void setUp() {
        member = Member.of("Kim");
        coupon = Coupon.of(
                "테스트 쿠폰",
                20,
                5000L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                member
        );
    }

    @Test
    void calculateDiscountAmount() {
        long originalPrice = 20000L;
        long discountAmount = coupon.calculateDiscountAmount(originalPrice);

        assertThat(discountAmount).isEqualTo(4000L);
    }

    @Test
    void calculateDiscountAmount_overMaxDiscountAmount() {
        long originalPrice = 100_000L;
        long discountAmount = coupon.calculateDiscountAmount(originalPrice);

        assertThat(discountAmount).isEqualTo(5000L);
    }

    @Test
    void calculateDiscountAmount_originalPriceIsZero_Error() {
        long originalPrice = 0L;

        assertThatThrownBy(() -> coupon.calculateDiscountAmount(originalPrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(COUPON_INVALID_AMOUNT_ERROR);
    }

    @Test
    void useCoupon() {
        coupon.use();

        assertThat(coupon.isUsed()).isTrue();
    }

    @Test
    void expired() {

        Coupon expiredCoupon = Coupon.of(
                "만료된 쿠폰",
                20,
                5000L,
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().minusDays(1),
                member);

        coupon.use();

        assertThat(expiredCoupon.isAvailable()).isFalse();
        assertThat(coupon.isAvailable()).isFalse();
    }
}