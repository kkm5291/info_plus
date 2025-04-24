package com.example.infoplus.profile.service;

import com.example.infoplus.profile.domain.Profile;
import com.example.infoplus.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Profile getProfileById(Long id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디를 가진 사용자를 찾을 수 없습니다" + id));
    }

}
