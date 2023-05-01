package com.project.fri.gameRoom.service;

import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.gameRoom.dto.FindAllUserByGameRoomId;
import com.project.fri.gameRoom.dto.FindGameRoomResponse;
import com.project.fri.gameRoom.entity.GameRoom;
import com.project.fri.gameRoom.repository.GameRoomRepository;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        List<User> users = userRepository.findAllByGameRoom_Id(gameRoomId)
                .orElseThrow(() -> new NotFoundExceptionMessage(
                        NotFoundExceptionMessage.NOT_FOUND_USER
                ));
        List<FindAllUserByGameRoomId> findUsers = users.stream()
                .map(user -> FindAllUserByGameRoomId.create(user))
                .collect(Collectors.toList());

        // 해당 gameRoom에 내가 참여했는지 확인
        Long userId = 1l; // todo: 임의로 줌
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
}
