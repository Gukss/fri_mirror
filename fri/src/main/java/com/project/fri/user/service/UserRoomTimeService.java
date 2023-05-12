package com.project.fri.user.service;

import com.project.fri.room.entity.Room;
import com.project.fri.user.entity.User;
import org.springframework.http.HttpStatus;

public interface UserRoomTimeService {
  HttpStatus createUserRoomTime(User user, Room room);
  HttpStatus updateUserRoomTime(User user, Room room);
}
