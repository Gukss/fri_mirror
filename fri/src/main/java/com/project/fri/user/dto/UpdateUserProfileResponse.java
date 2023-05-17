package com.project.fri.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UpdateUserProfileResponse {
    private String anonymousProfileImageUrl;
    private String nickname;
}
