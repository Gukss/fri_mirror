package com.project.fri.gameRoom.service;

import com.project.fri.gameRoom.dto.FindGameRoomResponse;

/**
 *packageName    : com.project.fri.gameRoom.service
 * fileName       : GameRoomService
 * author         : hagnoykmik
 * date           : 2023-04-25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-04-25        hagnoykmik       최초 생성
 */
public interface GameRoomService {

    /**
     * 게임 방 정보 조회
     * @param gameRoomId
     * @return
     */
    FindGameRoomResponse findGameRoom(Long gameRoomId);
}
