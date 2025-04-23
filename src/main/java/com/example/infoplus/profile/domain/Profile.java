package com.example.infoplus.profile.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Profile {

    protected Profile() {}

    protected Profile(String userName) {
        this.userName = userName;
    }

    public static Profile of(String userName) {
        return new Profile(userName);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private Long viewCount = 0L;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id) && Objects.equals(userName, profile.userName) && Objects.equals(viewCount, profile.viewCount) && Objects.equals(createdDate, profile.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, viewCount, createdDate);
    }
}
