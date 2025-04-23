package com.example.infoplus.profile.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Profile {

    protected Profile() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private Long viewCount = 0L;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

}
