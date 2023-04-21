package com.project.fri.user.service;

import com.project.fri.user.dto.UpdateUserRoomRequest;
import com.project.fri.user.dto.UpdateUserRoomResponse;
import com.project.fri.user.entity.User;

/**
 * packageName    : com.project.fri.user.service fileName       : UserService date           :
 * 2023-04-18 description    :
 */
public interface UserService {

  /**
   * desc: id로 user 찾아오기
   * @param userId
   * @return
   */
  User findById(long userId);

  /**
   * 방 입장시 User의 room 업데이트
   * @param roomId
   * @param request
   * @return
   */
  UpdateUserRoomResponse updateUserRoom(Long roomId, UpdateUserRoomRequest request, Long userId);
}
