package com.example.infoplus.domain.member.entity;

import com.example.infoplus.domain.payment.entity.Payment;
import com.example.infoplus.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long point;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    private Member(String name) {
        this.name = name;
        this.point = 0L;
    }

    public static Member of(String name) {
        Member member = new Member(name);
        member.addProfile(Profile.of(member));
        return member;
    }

    public void convertAmountToPoint(Long amount) {
        this.point += amount;
    }

    public void addProfile(Profile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.addMember(this);
        }
    }
}
