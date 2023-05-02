package com.project.fri.gameRoom.service;

import com.project.fri.common.entity.Area;
import com.project.fri.common.entity.Category;
import com.project.fri.common.repository.AreaRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.gameRoom.dto.*;
import com.project.fri.gameRoom.entity.GameRoom;
import com.project.fri.gameRoom.repository.GameRoomRepository;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * packageName    : com.project.fri.gameRoom.service fileName       : GameRoomServiceImpl author
 *     : hagnoykmik date           : 2023-04-25 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-04-25        hagnoykmik       최초 생성
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class GameRoomServiceImpl implements GameRoomService{

    private final GameRoomRepository gameRoomRepository;
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;

    /**
     * 게임 방 정보 조회
     * @param gameRoomId
     * @return
     */
    @Override
    public FindGameRoomResponse findGameRoom(Long gameRoomId) {
        
        // gameRoomId로 gameRoom 찾기
        GameRoom findGameRoom = gameRoomRepository.findById(gameRoomId)
                .orElseThrow(() -> new NotFoundExceptionMessage(
                        NotFoundExceptionMessage.NOT_FOUND_ROOM));

        // 해당 gameRoom에 참여한 유저 찾고 + 원하는 dto 형식으로 반환
        List<User> users = userRepository.findAllByGameRoom_Id(gameRoomId);
        List<FindAllUserByGameRoomId> findUsers = users.stream()
                .map(user -> FindAllUserByGameRoomId.create(user))
                .collect(Collectors.toList());

        // 해당 gameRoom에 내가 참여했는지 확인
        Long userId = 4l; // todo: 임의로 줌
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionMessage(
                        NotFoundExceptionMessage.NOT_FOUND_USER
                ));
        boolean isParticipate = false;
        if (user.getGameRoom().getId().equals(gameRoomId)) {
            isParticipate = true;
        }

        // response dto로 변환
        FindGameRoomResponse findGameRoomResponse = FindGameRoomResponse.create(findGameRoom, findUsers, isParticipate);
        return findGameRoomResponse;
    }

    /**
     * 게임 방 더보기
     * @param page
     * @param size
     * @return 게임 방 리스트 20개씩 잘라서 주기
     */
    @Override
    public List<FindAllGameRoomResponse> findAllGameRoom(Category stringArea, int page, int size) {
        // 지역명으로 area 찾기
        Area area = areaRepository.findByCategory(stringArea)
                .orElseThrow(() -> new NotFoundExceptionMessage(
                        NotFoundExceptionMessage.NOT_FOUND_AREA
                ));
        
        // 지역별 게임방
        Pageable pagable = PageRequest.of(page, size);  // page별로 20개씩 잘라주기
        List<GameRoom> findAllGameRoomByArea = gameRoomRepository.findAllByArea(area, pagable);

        List<FindAllGameRoomResponse> findAllGameRoom = findAllGameRoomByArea.stream()
                .map(gameRoom -> FindAllGameRoomResponse.create(gameRoom))
                .collect(Collectors.toList());

        return findAllGameRoom;
    }

    /**
     * 게임 방 생성
     * @param request
     * @param user
     * @return
     */
    @Override
    public CreateGameRoomResponse createGameRoom(CreateGameRoomRequest request, User user) {
        // area로 지역 객체 만들기
        Area area = areaRepository.findByCategory(request.getArea())
                .orElseThrow(() -> new NotFoundExceptionMessage(
                        NotFoundExceptionMessage.NOT_FOUND_AREA
                ));

        // db에 저장
        GameRoom gameRoom = GameRoom.create(request, area, user);
        gameRoomRepository.save(gameRoom);

        // 응답 dto로 변환
        CreateGameRoomResponse createGameRoom = CreateGameRoomResponse.create(gameRoom, user);
        return createGameRoom;
    }


}
