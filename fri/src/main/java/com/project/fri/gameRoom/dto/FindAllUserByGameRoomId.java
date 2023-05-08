package com.project.fri.gameRoom.dto;

import com.project.fri.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class FindAllUserByGameRoomId {

    private String name;
    private String anonymousProfileImageUrl;

    public static FindAllUserByGameRoomId create(User users) {
        return FindAllUserByGameRoomId.builder()
                .name(users.getName())
                .anonymousProfileImageUrl(users.getAnonymousProfileImage().getImageUrl())
                .build();
    }
}
