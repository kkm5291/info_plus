package com.example.infoplus.domain.profile.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

public class ProfileResponseDto {

    @Getter
    public static class ProfileList {
        private final Long id;
        private final String name;
        private final Long viewCount;
        private final LocalDateTime createdDate;

        @QueryProjection
        public ProfileList(Long id, String name, Long viewCount, LocalDateTime createdDate) {
            this.id = id;
            this.name = name;
            this.viewCount = viewCount;
            this.createdDate = createdDate;
        }
    }
}
