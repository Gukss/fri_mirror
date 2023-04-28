package com.project.fri.user.service;

import com.project.fri.user.dto.CreateUserRequest;
import com.project.fri.user.dto.UpdateUserRoomRequest;
import com.project.fri.user.dto.UpdateUserRoomResponse;
import com.project.fri.user.dto.UpdateUserReadyResponse;
import com.project.fri.user.entity.User;
import org.springframework.http.ResponseEntity;

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
  ResponseEntity<UpdateUserRoomResponse> updateUserRoom(Long roomId, UpdateUserRoomRequest request, Long userId);

  /**
   * desc: id로 user 찾아서 들고있는 방 번호가 입력받은 방 번호와 일치하는지 확인하고 일치하면 ready를 not해서 바꿔주기
   *
   * @param roomId
   * @return
   */
  UpdateUserReadyResponse updateUserReady(long userId, long roomId);

  /**
   * desc: 회원가입
   * @return
   */
  void createUser(CreateUserRequest request);
}
