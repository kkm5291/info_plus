package com.example.infoplus.domain.profile.controller;

import com.example.infoplus.domain.member.repository.MemberRepository;
import com.example.infoplus.domain.profile.ProfileSortType;
import com.example.infoplus.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;
    private final MemberRepository memberRepository;

    @GetMapping
    public ResponseEntity<?> getProfiles(
            @RequestParam(defaultValue = "NAME_ASC") ProfileSortType sortType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return profileService.getProfiles(sortType, page, size);
    }
}
