package com.project.fri.gameRoom.dto;

import lombok.Data;

@Data
public class UpdateGameRoomParticipationRequest {

    private boolean participate; // boolean값은 알아서 isParticipate로 만들어준다
}
