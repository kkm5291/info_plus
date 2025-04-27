package com.example.infoplus.domain.profile.service;

import com.example.infoplus.domain.profile.entity.Profile;
import com.example.infoplus.domain.profile.ProfileSortType;
import com.example.infoplus.domain.profile.repository.ProfileQueryRepository;
import com.example.infoplus.domain.profile.repository.ProfileRepository;
import com.example.infoplus.domain.profile.dto.request.ProfileRequestDto;
import com.example.infoplus.domain.profile.dto.response.ProfileResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileQueryRepository profileQueryRepository;
    private final ProfileRepository profileRepository;

    @Override
    public ResponseEntity<?> getProfiles(ProfileSortType sortType, int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);

        Page<ProfileResponseDto.ProfileList> allProfilesWithPagingAndSorting = profileQueryRepository.findAllProfilesWithPagingAndSorting(sortType, pageRequest);

        return ResponseEntity.ok(allProfilesWithPagingAndSorting);
    }

    @Override
    @Transactional
    public ResponseEntity<?> increaseProfileViewCount(ProfileRequestDto.viewDetail viewDetail) {
        Long profileId = viewDetail.getId();

        Profile findProfile = profileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID 입니다: " + profileId));

        findProfile.increaseViewCount();
        return ResponseEntity.ok().build();
    }

}
