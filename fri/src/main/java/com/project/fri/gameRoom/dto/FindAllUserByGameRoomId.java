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
    private String profileUrl;

    public static FindAllUserByGameRoomId create(User users) {
        FindAllUserByGameRoomId findAllUserByGameRoomId = FindAllUserByGameRoomId.builder()
                .name(users.getName())
                .profileUrl(users.getProfileUrl())
                .build();
        return findAllUserByGameRoomId;
    }
}
