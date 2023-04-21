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

    if (findUser.getRoom() == null) {

    }
    // 이미 유저가 룸에 들어있을 경우 요청들어온 room과 같은지 확인
    if (findUser.getRoom() != null && findUser.getRoom().equals(findRoom)) {
      throw new IllegalStateException("입장중인 방과 일치하지 않습니다.");
    }

  }

}
