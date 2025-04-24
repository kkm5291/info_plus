package com.example.infoplus.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Integer point;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    private Member(String name, Integer point) {
        this.name = name;
        this.point = point;
    }

    public static Member of(String name, Integer point) {
        Member member = new Member(name, point);
        member.addProfile(Profile.of(member));
        return member;
    }

    public void addProfile(Profile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.addMember(this);
        }
    }


}
