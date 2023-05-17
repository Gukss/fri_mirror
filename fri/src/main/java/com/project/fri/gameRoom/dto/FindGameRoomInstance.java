package com.project.fri.gameRoom.dto;

import com.project.fri.gameRoom.entity.GameRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FindGameRoomInstance {
    private Long gameRoomId;
    private String title;
    private int headCount;
    private String location;
    private int participationCount;


    public static FindGameRoomInstance create(GameRoom gameroom, int participantCount) {
        return FindGameRoomInstance.builder()
                .gameRoomId(gameroom.getId())
                .title(gameroom.getTitle())
                .headCount(gameroom.getHeadCount())
                .location(gameroom.getLocation())
                .participationCount(participantCount)
                .build();
    }
}
