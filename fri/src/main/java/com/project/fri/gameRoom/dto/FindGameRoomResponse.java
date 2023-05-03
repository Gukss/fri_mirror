package com.project.fri.gameRoom.dto;

import com.project.fri.gameRoom.entity.GameRoom;
import com.project.fri.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class FindGameRoomResponse {

    private Long roomId;
    private String title;
    private String location;
    private int headCount;
    private boolean isParticipate;
    private List<FindAllUserByGameRoomId> participation;
    private int participantCount;

    public static FindGameRoomResponse create(GameRoom gameRoom, List<FindAllUserByGameRoomId> users, boolean isParticipate) {
        FindGameRoomResponse findGameRoomResponse = FindGameRoomResponse.builder()
                .roomId(gameRoom.getId())
                .title(gameRoom.getTitle())
                .location(gameRoom.getLocation())
                .headCount(gameRoom.getHeadCount())
                .isParticipate(isParticipate)
                .participation(users)
                .participantCount(users.size())
                .build();
        return findGameRoomResponse;
    }
}
