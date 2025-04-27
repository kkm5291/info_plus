package com.example.infoplus.domain.profile.entity;

import com.example.infoplus.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "profile")
    private Member member;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

    protected Profile() {}

    private Profile(Member member) {
        this.member = member;
        this.viewCount = 0L;
    }

    public static Profile of(Member member) {
        return new Profile(member);
    }

    public void addMember(Member member) {
        this.member = member;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
