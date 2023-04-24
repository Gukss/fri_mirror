package com.project.fri.user.service;

import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.room.entity.Room;
import com.project.fri.room.repository.RoomRepository;
import com.project.fri.user.dto.UpdateUserRoomRequest;
import com.project.fri.user.dto.UpdateUserRoomResponse;
import com.project.fri.user.dto.UpdateUserReadyResponse;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.project.fri.user.service fileName       : UserServiceImpl date           :
 * 2023-04-19 description    :
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final RoomRepository roomRepository;

  private final UserRepository userRepository;

  @Override
  public User findById(long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
  }

  @Override
  @Transactional
  public UpdateUserRoomResponse updateUserRoom(Long roomId, UpdateUserRoomRequest request, Long userId) {
    Optional<User> user = userRepository.findById(userId);
    User findUser = user.orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    Optional<Room> room = roomRepository.findById(roomId);
    Room findRoom = room.orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM));

    // 삭제된 방인지 먼저 확인
    if (Boolean.TRUE.equals(findRoom.isDelete())) {
      throw new NotFoundExceptionMessage((NotFoundExceptionMessage.NOT_FOUND_ROOM));
    }

    boolean participate = true;

    if (findUser.getRoom() == null ) {
      // 해당 유저가 어떤방에도 입장하지 않은 상태일 때 -> 바로 입장
      if (Boolean.TRUE.equals(request.getIsParticipate())) {
        findUser.updateRoomNumber(findRoom);
      } else {
        participate = false;
      }
    } else if (findUser.getRoom().equals(findRoom) && Boolean.FALSE.equals(request.getIsParticipate())) {
      // 입장중인 방과 동일하면 퇴장 만약시 남은 인원이 없으면 방 삭제 및 유저 ready상태 false
      findUser.updateRoomNumber(null);
      //ready 상태 false만들기 -> merge 하고난 후
      participate = false;
      // 해당방에 유저가 남아 있는지 확인 없으면 방 삭제
      List<User> findUsers = userRepository.findAllByRoom(findRoom).orElse(new ArrayList<>());
      if (findUsers.isEmpty()) {
        findRoom.deleteRoom();
      }
    } else {
      // 방에 입장 상태이지만 기존 입장중이 방과 입장하려는 방이 일치하지 않을 때
      throw new IllegalStateException("입장중인 방과 일치하지 않습니다.");
    }

    return UpdateUserRoomResponse.builder()
            .roomId(findRoom.getId())
            .title(findRoom.getTitle())
            .isParticipate(participate)
            .ready(false)
            .build();
  }

  public UpdateUserReadyResponse updateUserReady(long userId, long roomId) {
    //todo: 처음에 pathVariable로 받아온 roomId와 user에서 꺼내온 roomId를 비교한다.
    // 비교하고 다르면 404를 던진다?
    User foundUser = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
    Room room = foundUser.getRoom();
    Long foundUserRoomId = room.getId();
    boolean curReady = foundUser.isReady(); //현재 ready 상태
    boolean curConfirmed = room.isConfirmed();
    UpdateUserReadyResponse updateUserReadyResponse = null; //반환할 객체 미리 선언하기

    if (foundUserRoomId == roomId) { //같을 때 => 정상 진행
      //해당 방에 속한 user 찾아와서 본인 빼고 모두 ready 눌렀는지 확인하기
      List<User> userList = userRepository.findAllByRoom(room)
          .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
      boolean isAllReady = true;
      //todo: 본인은 반복문에서 검사를 안하는데
      // 본인이 true인 경우 다시 false로 바꿔주면 안되기 때문에
      // 적절한 조치가 필요해보인다.
      for(User x: userList){
        log.info("userID: "+x.getId());
        if(x.equals(foundUser)){ //본인이면
          continue;
        }
        if(!x.isReady()){ //ready가 안됐으면 return => 다른 사람들이 ready하도록 기다려야 한다.
          isAllReady = false;
          break;
        }
      }
      //일단 나의 ready를 not해서 바꿔준다.
      boolean nextReady = !curReady;
      boolean nextConfirmed = curConfirmed;

      foundUser.updateReady(nextReady);
      if(isAllReady){
        //isAllReady가 true면 나 빼고 모두 완료했다는 의미다. => 현재 방의 isConfirmed를 바꿔준다.
        nextConfirmed = !curConfirmed;
        room.updateIsConfirmed(nextConfirmed);
      }

      updateUserReadyResponse = UpdateUserReadyResponse.builder()
          .ready(nextReady)
          .isConfirmed(nextConfirmed)
          .roomId(roomId)
          .build();

    } else { //여기로 넘어오면 비정상적인 요청이다.
      //todo: 반환 객체 updateUserReadyResponse 에 error 담아서 보내기?
    }
    return updateUserReadyResponse;
  }

}
