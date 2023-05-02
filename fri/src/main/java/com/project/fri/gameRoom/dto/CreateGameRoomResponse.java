package com.project.fri.gameRoom.dto;

import com.project.fri.gameRoom.entity.GameRoom;
import com.project.fri.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateGameRoomResponse {
    private Long gameRoomId;
    private String title;
    private int headCount;
    private String nickname;

    public static CreateGameRoomResponse create(GameRoom gameRoom, User user) {
        CreateGameRoomResponse createGameRoomResponse = CreateGameRoomResponse.builder()
                .gameRoomId(gameRoom.getId())
                .title(gameRoom.getTitle())
                .headCount(gameRoom.getHeadCount())
                .nickname(user.getNickname())
                .build();
        return createGameRoomResponse;
    }
}
