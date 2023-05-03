package com.project.fri.gameRoom.service;

import com.project.fri.common.entity.Category;
import com.project.fri.gameRoom.dto.*;
import com.project.fri.user.entity.User;

import java.util.List;
import org.springframework.data.domain.Pageable;

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
    FindGameRoomResponse findGameRoom(Long gameRoomId, Long userId);

    /**
     * 게임 방 더보기
     * @param page
     * @param size
     * @return 게임 방 리스트 20개씩 잘라서 주기
     */
    List<FindAllGameRoomResponse> findAllGameRoom(Category stringArea, int page, Pageable pageable);

    /**
     * 게임 방 생성
     * @param request
     * @param userId
     * @return
     */
    CreateGameRoomResponse createGameRoom(CreateGameRoomRequest request, Long userId);

    /**
     * 게임 방 들어가기, 나가기
     * @param gameRoomId
     * @param request
     * @param userId
     * @return
     */
    UpdateGameRoomParticipationResponse updateGameRoomParticipation(Long gameRoomId, UpdateGameRoomParticipationRequest request, Long userId);

    /**
     * desc 게임방 목록 조회
     * @param areaCategory
     * @return
     */
    List<FindGameRoomInstance> findGameRoomList(Category areaCategory);
}
