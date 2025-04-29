package com.example.infoplus.domain.coupon.entity;

import com.example.infoplus.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    public static final int PERCENTAGE_BASE = 100;
    public static final String COUPON_INVALID_AMOUNT_ERROR = "쿠폰을 적용할 수 없는 금액입니다";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int discountRate;

    private Long maxDiscountAmount;

    private LocalDateTime issuedAt;

    private LocalDateTime expiredAt;

    private boolean isUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Coupon(String name, int discountRate, Long maxDiscountAmount, LocalDateTime issuedAt, LocalDateTime expiredAt, boolean isUsed, Member member) {
        this.name = name;
        this.discountRate = discountRate;
        this.maxDiscountAmount = maxDiscountAmount;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.isUsed = isUsed;
        this.member = member;
    }

    public static Coupon of(String name, int discountRate, Long maxDiscountAmount, LocalDateTime issuedAt, LocalDateTime expiredAt, Member member) {
        return new Coupon(name,
                discountRate,
                maxDiscountAmount,
                issuedAt,
                expiredAt,
                false,
                member);
    }

    public void use() {
        this.isUsed = true;
    }

    public boolean isExpired() {
        return expiredAt.isBefore(LocalDateTime.now());
    }

    public boolean isAvailable() {
        return !isUsed && !isExpired();
    }

    public long calculateDiscountAmount(long originalPrice) {
        if (originalPrice <= 0) {
            throw new IllegalArgumentException(COUPON_INVALID_AMOUNT_ERROR);
        }

        long discountAmount = (originalPrice * discountRate) / PERCENTAGE_BASE;
        return Math.min(discountAmount, maxDiscountAmount);
    }
}
