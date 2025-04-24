package com.example.infoplus.repository;

import com.example.infoplus.domain.Profile;
import com.example.infoplus.domain.QProfile;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ProfileQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Profile> findAllProfilesWithSort(String sortField) {
        QProfile profile = QProfile.profile;
        return jpaQueryFactory.selectFrom(profile)
                .fetch();
    }
}
