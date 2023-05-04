package com.project.fri.user.dto;

import lombok.Data;

@Data
public class UpdateUserProfileRequest {
    private Long anonymousProfileImageId;
    private String nickname;
}
