package com.project.fri.user.service;

import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.room.entity.Room;
import com.project.fri.room.repository.RoomRepository;
import com.project.fri.user.entity.User;
import com.project.fri.user.entity.UserRoomTime;
import com.project.fri.user.repository.UserRepository;
import com.project.fri.user.repository.UserRoomTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserRoomTimeServiceImpl implements UserRoomTimeService{
  private final UserRepository userRepository;
  private final RoomRepository roomRepository;
  private final UserRoomTimeRepository userRoomTimeRepository;
  @Override
  @Transactional
  public HttpStatus createUserRoomTime(User user, Room room) {
    User findUser = userRepository.findById(user.getId())
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    Room findRoom=roomRepository.findById(room.getId())
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM));

    UserRoomTime time = UserRoomTime.create(findUser, findRoom);
    userRoomTimeRepository.save(time);
    return HttpStatus.CREATED;
  }

  @Override
  @Transactional
  public HttpStatus updateUserRoomTime(User user, Room room) {
    User findUser = userRepository.findById(user.getId())
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    Room findRoom=roomRepository.findById(room.getId())
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM));

    UserRoomTime time = userRoomTimeRepository.findByUserAndRoomAndIsDeleteFalse(findUser, findRoom)
        .orElseThrow(
            () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER_ROOM_TIME));

    time.updateIsDelete(true);
    return HttpStatus.CREATED;
  }
}
