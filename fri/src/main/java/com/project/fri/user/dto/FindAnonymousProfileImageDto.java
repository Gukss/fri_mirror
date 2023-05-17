package com.project.fri.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FindAnonymousProfileImageDto {
    private Long anonymousImageId;
    private String anonymousImageUrl;
}
