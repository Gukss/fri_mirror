package com.project.fri.user.service;

import com.project.fri.common.entity.Area;
import com.project.fri.common.repository.AreaRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.room.entity.Room;
import com.project.fri.room.repository.RoomRepository;
import com.project.fri.user.dto.CreateUserRequest;
import com.project.fri.user.dto.UpdateUserReadyResponse;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  private final AreaRepository areaRepository;
  private final UserRepository userRepository;

  @Override
  public User findById(long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
  }

  @Override
  @Transactional
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

  /**
   * desc: 회원가입
   * @return
   */
  @Override
  public void createUser(CreateUserRequest request) {

    // area 찾기
    Area area = areaRepository.findByCategory(request.getArea());

    // user db에 저장
    User user = User.create(request, area);
    User saveUser = userRepository.save(user);
  }
}
