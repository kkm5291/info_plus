package com.example.infoplus.domain.profile.repository;

import com.example.infoplus.domain.profile.ProfileSortType;
import com.example.infoplus.domain.profile.dto.response.ProfileResponseDto;
import com.example.infoplus.domain.profile.dto.response.QProfileResponseDto_ProfileList;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.infoplus.domain.profile.ProfileSortType.*;
import static com.example.infoplus.domain.profile.entity.QProfile.profile;

@Repository
@RequiredArgsConstructor
public class ProfileQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<ProfileResponseDto.ProfileList> findAllProfilesWithPagingAndSorting(ProfileSortType sortType, Pageable pageable) {

        List<ProfileResponseDto.ProfileList> profiles = jpaQueryFactory
                .select(new QProfileResponseDto_ProfileList(
                        profile.id,
                        profile.member.name,
                        profile.viewCount,
                        profile.createdDate
                ))
                .from(profile)
                .orderBy(createSpecifier(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory.select(profile.id.count())
                .from(profile);

        return PageableExecutionUtils.getPage(profiles, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> createSpecifier(ProfileSortType sortType) {
        if (sortType == NAME_ASC) {
            return profile.member.name.asc();
        }

        if (sortType == VIEW_COUNT_DESC) {
            return profile.viewCount.desc();
        }

        if (sortType == DATE_CREATED_DESC) {
            return profile.createdDate.desc();
        }

        return profile.id.desc();
    }
}
