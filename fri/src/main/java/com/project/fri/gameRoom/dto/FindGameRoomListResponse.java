package com.project.fri.gameRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FindGameRoomListResponse {
    private List<FindGameRoomInstance> game;
}
