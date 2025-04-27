package com.example.infoplus.domain.profile.service;

import com.example.infoplus.domain.profile.ProfileSortType;
import org.springframework.http.ResponseEntity;

public interface ProfileService {
    ResponseEntity<?> getProfiles(ProfileSortType sortType, int offset, int limit);
}
