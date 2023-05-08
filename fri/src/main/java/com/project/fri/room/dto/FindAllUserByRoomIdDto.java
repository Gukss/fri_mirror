package com.project.fri.room.dto;

import com.project.fri.user.entity.User;
import lombok.Data;

@Data
public class FindAllUserByRoomIdDto {
    private String name;
    private String anonymousProfileImageUrl;

    public FindAllUserByRoomIdDto(User user) {
        name = user.getName();
        anonymousProfileImageUrl = user.getAnonymousProfileImage().getImageUrl();
    }
}
