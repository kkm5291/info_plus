package com.example.infoplus.domain.profile.service;

import com.example.infoplus.domain.profile.entity.Profile;
import com.example.infoplus.domain.profile.repository.ProfileQueryRepository;
import com.example.infoplus.domain.profile.repository.ProfileRepository;
import com.example.infoplus.domain.profile.dto.request.ProfileRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private ProfileQueryRepository profileQueryRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Test
    void increaseProfileViewCount() {
        Long profileId = 1L;
        ProfileRequestDto.viewDetail viewDetail = new ProfileRequestDto.viewDetail();
        viewDetail.setId(profileId);

        Profile mockProfile = mock(Profile.class);
        given(profileRepository.findById(profileId)).willReturn(Optional.of(mockProfile));

        assertThatCode(() -> {
            ResponseEntity<?> response = profileService.increaseProfileViewCount(viewDetail);

            verify(mockProfile).increaseViewCount();
            verify(profileRepository).findById(profileId);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }).doesNotThrowAnyException();
    }

    @Test
    void increaseProfileViews_profileNotFound_Error() {
        Long invalidProfileId = 999L;
        ProfileRequestDto.viewDetail viewDetail = new ProfileRequestDto.viewDetail();
        viewDetail.setId(invalidProfileId);

        doThrow(new IllegalArgumentException("찾을 수 없는 사용자"))
                .when(profileRepository).findById(invalidProfileId);

        assertThatThrownBy(() -> profileService.increaseProfileViewCount(viewDetail));
        verify(profileRepository).findById(invalidProfileId);

        verifyNoMoreInteractions(profileRepository);
    }
}