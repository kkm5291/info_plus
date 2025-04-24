package com.example.infoplus.profile.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProfileQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
}
