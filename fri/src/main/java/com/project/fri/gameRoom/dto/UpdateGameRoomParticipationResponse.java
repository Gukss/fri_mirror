package com.project.fri.gameRoom.dto;

import com.project.fri.gameRoom.entity.GameRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateGameRoomParticipationResponse {

    private Long gameRoomId;
    private String title;
    private boolean participate;
    private double randomTime;
    private int headCount;

    public static UpdateGameRoomParticipationResponse create(boolean isParticipate, GameRoom gameRoom) {

        if (gameRoom == null) { // gameRoom.equals(null)은 내용비교라서 항상 false
            return UpdateGameRoomParticipationResponse.builder()
//                    .gameRoomId()
                .title("참여중인 게임방이 없습니다.")
                .participate(isParticipate)
//                    .randomTime(gameRoom.getRandomTime())
                .build();

        } else {
            return UpdateGameRoomParticipationResponse.builder()
                .gameRoomId(gameRoom.getId())
                .title(gameRoom.getTitle())
                .participate(isParticipate)
                .randomTime(gameRoom.getRandomTime())
                .headCount(gameRoom.getHeadCount())
                .build();
        }
    }
}
