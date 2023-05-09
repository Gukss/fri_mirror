package com.project.fri.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FindAnonymousProfileImagesResponse {
    private List<FindAnonymousProfileImageDto> anonymousImages;
}
