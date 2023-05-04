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

    public static UpdateGameRoomParticipationResponse create(boolean isParticipate, GameRoom gameRoom) {

        if (gameRoom == null) { // gameRoom.equals(null)은 내용비교라서 항상 false
            UpdateGameRoomParticipationResponse updateGameRoomParticipationResponse = UpdateGameRoomParticipationResponse.builder()
//                    .gameRoomId() todo: 어떻게 주지?
                    .title("참여중인 게임방이 없습니다.")
                    .participate(isParticipate)
                    .randomTime(gameRoom.getRandomTime())
                    .build();
            return updateGameRoomParticipationResponse;

        } else {
            UpdateGameRoomParticipationResponse updateGameRoomParticipationResponse = UpdateGameRoomParticipationResponse.builder()
                    .gameRoomId(gameRoom.getId())
                    .title(gameRoom.getTitle())
                    .participate(isParticipate)
                    .randomTime(gameRoom.getRandomTime())
                    .build();
            return updateGameRoomParticipationResponse;
        }
    }
}
