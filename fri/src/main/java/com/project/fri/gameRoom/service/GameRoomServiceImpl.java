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
import org.apache.logging.log4j.Logger;
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
 * packageName    : com.project.fri.gameRoom.service fileName       : GameRoomServiceImpl author :
 * hagnoykmik date           : 2023-04-25 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2023-04-25        hagnoykmik
 * 최초 생성
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class GameRoomServiceImpl implements GameRoomService {

  private final GameRoomRepository gameRoomRepository;
  private final UserRepository userRepository;
  private final AreaRepository areaRepository;

  /**
   * 게임 방 정보 조회
   *
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
        .map(FindAllUserByGameRoomId::create)  // todo: 리팩토링 필요
        .collect(Collectors.toList());

    // 해당 gameRoom에 내가 참여했는지 확인
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(
            NotFoundExceptionMessage.NOT_FOUND_USER
        ));
    boolean isParticipate = false;
    if (user.getGameRoom() != null) { //gameRoom이 없으면 가져올 수 없다.
      if (user.getGameRoom().getId().equals(gameRoomId)) {
        isParticipate = true;
      }
    }

    // response dto로 변환
    FindGameRoomResponse findGameRoomResponse = FindGameRoomResponse.create(findGameRoom, findUsers,
        isParticipate);
    return findGameRoomResponse;
  }

  /**
   * 게임 방 더보기
   *
   * @param stringArea
   * @param page
   * @param pageable
   * @return 게임 방 리스트 20개씩 잘라서 주기
   */
  @Override
  public List<FindAllGameRoomResponse> findAllGameRoom(Long userId, Category stringArea, int page,
      Pageable pageable) {
    // 지역명으로 area 찾기
    Area area = areaRepository.findByCategory(stringArea)
        .orElseThrow(() -> new NotFoundExceptionMessage(
            NotFoundExceptionMessage.NOT_FOUND_AREA
        ));

    // 지역별 게임방
    Pageable newPagable = PageRequest.of(page, pageable.getPageSize(),
        pageable.getSort());  // page별로 20개씩 잘라주기
    List<GameRoom> findAllGameRoomByArea = gameRoomRepository.findByAreaAndIsDeleteFalse(area, newPagable);

    // user가 속한 게임방 찾기
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(
            NotFoundExceptionMessage.NOT_FOUND_USER
        ));

    // 자신이 속한 게임방은 보여주지 않기
//    if (findAllGameRoomByArea.contains(user.getGameRoom())) {  // 내가 속한 게임방이 리스트에 포함되면 -> 이 처리 필요X
    findAllGameRoomByArea.remove(user.getGameRoom());        // 리스트에서 제거해준다

    // todo: 쿼리 dsl
    // 응답 dto로 변환
    List<FindAllGameRoomResponse> findAllGameRoom = findAllGameRoomByArea.stream()
        .map(gameRoom -> FindAllGameRoomResponse.create(gameRoom,
            userRepository.findAllByGameRoom(gameRoom).size()))
        .collect(Collectors.toList());

    return findAllGameRoom;
  }

  /**
   * 게임 방 생성
   *
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

    // 만약 다른 방에 참여하고 있으면 방생성할 수 없다
    if (user.getGameRoom() != null) {
      throw new InvalidRequestStateException("이미 참여중인 방이 있습니다.");
    }

    //랜덤 시간 만들기
    double time =
        Math.round((((Math.random() * 9) + 1) * 100)) / 100.0; //1~10초사이 랜덤값 => 소수점 아래 두 번째 자리

    // db에 저장
    GameRoom gameRoom = GameRoom.create(request, area, user, time);
    gameRoomRepository.save(gameRoom);

    // user db에 gameRoom 추가
    user.updateGameRoomNumber(gameRoom);

    // 응답 dto로 변환
    CreateGameRoomResponse createGameRoom = CreateGameRoomResponse.create(gameRoom, user);
    return createGameRoom;
  }

  /**
   * 게임 방 들어가기, 나가기
   *
   * @param gameRoomId
   * @param request
   * @param userId
   * @return
   */
  @Override
  @Transactional
  public UpdateGameRoomParticipationResponse updateGameRoomParticipation(Long gameRoomId,
      UpdateGameRoomParticipationRequest request, Long userId) {

    // userId로 user 찾기
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(
            NotFoundExceptionMessage.NOT_FOUND_USER
        ));
    // todo: 예외처리
    String message = "";
    // 참여 중인 다른 방이 있다면
    if ((user.getGameRoom() != null) && !(user.getGameRoom().getId().equals(gameRoomId))) {
      throw new InvalidRequestStateException(); // 유효하지 않은 요청 상태
    }
    // 방에 참여했는데 false 요청이 왔을 때
    if ((user.getGameRoom() != null) && (user.getGameRoom().getId().equals(gameRoomId))
        && !request.isParticipate()) {
      throw new InvalidRequestStateException();
    }

    // isParticipate가 true면 false로, false면 true로 바꿔준다
    boolean isParticipate = request.isParticipate();
    GameRoom gameRoom = gameRoomRepository.findById(gameRoomId)
        .orElseThrow(() -> new NotFoundExceptionMessage(
            NotFoundExceptionMessage.NOT_FOUND_ROOM
        ));

    // 현재 게임방에 참여하고 있는 user 인원
    int nowCount = userRepository.countByGameRoom(gameRoom);

    if (!isParticipate) {       // enterRoom: false이고 참여한 방이 없고ㅡ 현재 방 인원이 다 안찼을 때 -> 게임방번호 추가 (user.getGameRoom().getId() == null)안돼
      // 꽉 찬 방일 때
      if (nowCount >= gameRoom.getHeadCount()) {
        message = "정원이 초과된 방입니다.";
      }
      // 게임이 시작되어 삭제된 방일때
      if (gameRoom.isDelete()) {
        message = "게임이 완료된 방입니다.";
      }

      isParticipate = true;
      user.updateGameRoomNumber(gameRoom);

    } else {                    // exitRoom: true일 때 -> 게임방번호 null로
      isParticipate = false;
      user.updateGameRoomNumber(null);
      user.updateReady(false); // 방 나갈 때 ready를 false로 변경
      // 내가 나갈 때, 참여인원이 0명이면 방 삭제처리
      List<User> allParticipant = userRepository.findAllByGameRoom(gameRoom);
      if (allParticipant.isEmpty()) {  // 아무도 없으면 삭제처리
        gameRoom.updateIsDelete(true);
      }
      gameRoom = null;                 // 방나감 처리
    }

    // 응답 dto로 반환
    return UpdateGameRoomParticipationResponse.create(isParticipate, gameRoom, message);
  }

  /**
   * 게임방 목록 조회
   *
   * @param areaCategory
   * @return 게임방 10개 리스트
   */
  @Override
  public List<FindGameRoomInstance> findGameRoomList(Category areaCategory, Long userId) {
    Area findArea = areaRepository.findByCategory(areaCategory)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_AREA));

    User findUser = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    List<GameRoom> gameRoomList = gameRoomRepository.findByAreaAndIsDeleteFalseOrderByCreatedAtDesc(findArea);
    // todo : 게임방이 많으면 전체 게임방을 다들고와서 리스트를 만드는건 비효율적으로 보임

    // 내가 들어가 있는 game방 제거
    gameRoomList.remove(findUser.getGameRoom());

    List<FindGameRoomInstance> result = new ArrayList<>();

    for (GameRoom r : gameRoomList) {
      List<User> foundUserList = userRepository.findAllByGameRoom(r);
      int userSize = foundUserList.size(); //방에 참여한 인원수

      // 입장 인원이 다 차지않은 경우
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
