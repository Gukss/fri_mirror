package com.project.fri.gameRoom.service;

import com.project.fri.common.entity.Category;
import com.project.fri.gameRoom.dto.CreateGameRoomRequest;
import com.project.fri.gameRoom.dto.CreateGameRoomResponse;
import com.project.fri.gameRoom.dto.FindAllGameRoomResponse;
import com.project.fri.gameRoom.dto.FindGameRoomResponse;
import com.project.fri.user.entity.User;

import java.util.List;

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

    /**
     * 게임 방 더보기
     * @param page
     * @param size
     * @return 게임 방 리스트 20개씩 잘라서 주기
     */
    List<FindAllGameRoomResponse> findAllGameRoom(Category stringArea, int page, int size);

    /**
     * 게임 방 생성
     * @param request
     * @param user
     * @return
     */
    CreateGameRoomResponse createGameRoom(CreateGameRoomRequest request, User user);
}
