package com.example.infoplus.domain.profile.service;

import com.example.infoplus.domain.profile.ProfileSortType;
import com.example.infoplus.domain.profile.repository.ProfileQueryRepository;
import com.example.infoplus.domain.profile.repository.ProfileRepository;
import com.example.infoplus.domain.profile.response.ProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileQueryRepository profileQueryRepository;

    @Override
    public ResponseEntity<?> getProfiles(ProfileSortType sortType, int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);

        Page<ProfileResponseDto.ProfileList> allProfilesWithPagingAndSorting = profileQueryRepository.findAllProfilesWithPagingAndSorting(sortType, pageRequest);

        return ResponseEntity.ok(allProfilesWithPagingAndSorting);
    }

}
