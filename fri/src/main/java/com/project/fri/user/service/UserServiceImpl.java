package com.project.fri.user.service;

import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.room.entity.Room;
import com.project.fri.room.repository.RoomRepository;
import com.project.fri.user.dto.UpdateUserRoomRequest;
import com.project.fri.user.dto.UpdateUserRoomResponse;
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

  private final UserRepository userRepository;
  private final RoomRepository roomRepository;

  @Override
  public User findById(long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
  }

  @Override
  public UpdateUserRoomResponse updateUserRoom(Long roomId, UpdateUserRoomRequest request, Long userId) {
    Optional<User> user = userRepository.findByIdWithRoom(userId);
    User findUser = user.orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    Optional<Room> room = roomRepository.findById(roomId);
    Room findRoom = room.orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM));

    boolean participate = true;

    if (findUser.getRoom() == null) {
      // 해당 유저가 어떤방에도 입장하지 않은 상태일 때 -> 바로 입장
      findUser.updateRoomNumber(findRoom);
    } else if (findUser.getRoom().equals(findRoom)) {
      // 입장중인 방과 동일하면 퇴장 만약시 남은 인원이 없으면 방 삭제
      findUser.updateRoomNumber(null);
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
            .ready(true)
            .build();
  }

}
