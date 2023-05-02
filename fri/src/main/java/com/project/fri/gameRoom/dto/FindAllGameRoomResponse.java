package com.project.fri.gameRoom.dto;

import com.project.fri.gameRoom.entity.GameRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FindAllGameRoomResponse {

    private Long gameRoomId;
    private String title;
    private int headCount;
    private String location;

    public static FindAllGameRoomResponse create(GameRoom gameroom) {
        FindAllGameRoomResponse findAllGameRoomResponse = FindAllGameRoomResponse.builder()
                .gameRoomId(gameroom.getId())
                .title(gameroom.getTitle())
                .headCount(gameroom.getHeadCount())
                .location(gameroom.getLocation())
                .build();
        return findAllGameRoomResponse;
    }
}
