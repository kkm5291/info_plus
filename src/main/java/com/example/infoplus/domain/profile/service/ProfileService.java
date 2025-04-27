package com.example.infoplus.domain.profile.service;

import com.example.infoplus.domain.profile.ProfileSortType;
import com.example.infoplus.domain.profile.dto.request.ProfileRequestDto;
import org.springframework.http.ResponseEntity;

public interface ProfileService {
    ResponseEntity<?> getProfiles(ProfileSortType sortType, int offset, int limit);

    ResponseEntity<?> increaseProfileViewCount(ProfileRequestDto.viewDetail viewDetail);
}
