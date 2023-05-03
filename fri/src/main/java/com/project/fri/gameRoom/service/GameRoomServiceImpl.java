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
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
    public FindGameRoomResponse findGameRoom(Long gameRoomId, Long userId) {
        
        // gameRoomId로 gameRoom 찾기
        GameRoom findGameRoom = gameRoomRepository.findById(gameRoomId)
                .orElseThrow(() -> new NotFoundExceptionMessage(
                        NotFoundExceptionMessage.NOT_FOUND_ROOM));

        // 해당 gameRoom에 참여한 유저 찾고 + 원하는 dto 형식으로 반환
        List<User> users = userRepository.findAllByGameRoom_Id(gameRoomId);
        List<FindAllUserByGameRoomId> findUsers = users.stream()
                .map(user -> FindAllUserByGameRoomId.create(user))  // todo: 리팩토링 필요
                .collect(Collectors.toList());

        // 해당 gameRoom에 내가 참여했는지 확인
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
    public List<FindAllGameRoomResponse> findAllGameRoom(Category stringArea, int page, Pageable pageable) {
        // 지역명으로 area 찾기
        Area area = areaRepository.findByCategory(stringArea)
                .orElseThrow(() -> new NotFoundExceptionMessage(
                        NotFoundExceptionMessage.NOT_FOUND_AREA
                ));
        
        // 지역별 게임방
        Pageable newPagable = PageRequest.of(page, pageable.getPageSize(),pageable.getSort());  // page별로 20개씩 잘라주기
        List<GameRoom> findAllGameRoomByArea = gameRoomRepository.findAllByArea(area, newPagable);

        List<FindAllGameRoomResponse> findAllGameRoom = findAllGameRoomByArea.stream()
                .map(gameRoom -> FindAllGameRoomResponse.create(gameRoom))
                .collect(Collectors.toList());

        return findAllGameRoom;
    }

    /**
     * 게임 방 생성
     * @param request
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public CreateGameRoomResponse createGameRoom(CreateGameRoomRequest request, Long userId) {
        // area로 지역 객체 만들기
        Area area = areaRepository.findByCategory(request.getArea())
                .orElseThrow(() -> new NotFoundExceptionMessage(
                        NotFoundExceptionMessage.NOT_FOUND_AREA
                ));

        // userId로 user 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionMessage(
                        NotFoundExceptionMessage.NOT_FOUND_USER
                ));

        // db에 저장
        GameRoom gameRoom = GameRoom.create(request, area, user);
        gameRoomRepository.save(gameRoom);

        // 응답 dto로 변환
        CreateGameRoomResponse createGameRoom = CreateGameRoomResponse.create(gameRoom, user);
        return createGameRoom;
    }

    /**
     * 게임 방 들어가기, 나가기
     * @param gameRoomId
     * @param request
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public UpdateGameRoomParticipationResponse updateGameRoomParticipation(Long gameRoomId, UpdateGameRoomParticipationRequest request, Long userId) {

        // userId로 user 찾기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionMessage(
                        NotFoundExceptionMessage.NOT_FOUND_USER
                ));
        // todo: 예외처리
        // 참여 중인 다른 방이 있다면
        if ((user.getGameRoom() != null) && !(user.getGameRoom().getId().equals(gameRoomId))) {
            throw new InvalidRequestStateException(); // 유효하지 않은 요청 상태
        }
        // 방에 참여했는데 false 요청이 왔을 때
        if ((user.getGameRoom() != null) && (user.getGameRoom().getId().equals(gameRoomId)) && !request.isParticipate()) {
            throw new InvalidRequestStateException();
        }

        // isParticipate가 true면 false로, false면 true로 바꿔준다
        boolean isParticipate = request.isParticipate();
        GameRoom gameRoom = gameRoomRepository.findById(gameRoomId)
                .orElseThrow(() -> new NotFoundExceptionMessage(
                        NotFoundExceptionMessage.NOT_FOUND_ROOM
                ));

        if (!isParticipate) {       // false이고 참여한 방이 없을 때 -> 게임방번호 추가 (user.getGameRoom().getId() == null)안돼
            log.info("요청값 확인");
            isParticipate = true;
            user.updateGameRoomNumber(gameRoom);

        } else {                    // true일 때 -> 게임방번호 null로
            isParticipate = false;
            user.updateGameRoomNumber(null);
            gameRoom = null;
        }

        // 응답 dto로 반환
        UpdateGameRoomParticipationResponse updateGameRoomParticipation = UpdateGameRoomParticipationResponse.create(isParticipate, gameRoom);
        return updateGameRoomParticipation;
    }

    /**
     * 게임방 목록 조회
     * @param areaCategory
     * @return 게임방 10개 리스트
     */
    @Override
    public List<FindGameRoomInstance> findGameRoomList(Category areaCategory) {
        Area findArea = areaRepository.findByCategory(areaCategory)
                .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_AREA));
        List<GameRoom> gameRoomList = gameRoomRepository.findAllByAreaOrderByCreatedAtDesc(findArea);
        // todo : 게임방이 많으면 전체 게임방을 다들고와서 리스트를 만드는건 비효율적으로 보임
        List<FindGameRoomInstance> result = new ArrayList<>();

        for (GameRoom r : gameRoomList) {
            List<User> foundUserList = userRepository.findAllByGameRoom(r);
            int userSize = foundUserList.size(); //방에 참여한 인원수

            if (userSize < r.getHeadCount()) {
                result.add(FindGameRoomInstance.create(r, userSize));
            }

            if (result.size() > 9) {
                break;
            }
        }

        return result;
    }

}
