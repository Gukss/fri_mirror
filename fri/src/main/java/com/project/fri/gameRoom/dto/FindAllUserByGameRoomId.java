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
    private Long userId;
    private boolean isReady;

    public static FindAllUserByGameRoomId create(User users) {
        return FindAllUserByGameRoomId.builder()
                .name(users.getName())
                .anonymousProfileImageUrl(users.getAnonymousProfileImage().getImageUrl())
                .userId(users.getId())
                .isReady(users.isReady())
                .build();
    }
}
