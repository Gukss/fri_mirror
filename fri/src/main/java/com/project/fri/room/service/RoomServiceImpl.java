package com.project.fri.room.service;

import com.project.fri.common.entity.Area;
import com.project.fri.common.repository.AreaRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.room.dto.CreateRoomRequest;
import com.project.fri.room.dto.CreateRoomResponse;
import com.project.fri.room.dto.FindRoomResponse;
import com.project.fri.room.entity.Room;
import com.project.fri.room.entity.RoomCategory;
import com.project.fri.room.repository.RoomCategoryRepository;
import com.project.fri.room.repository.RoomRepository;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.fri.room.entity.Room;
import com.project.fri.room.repository.RoomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.project.fri.room.service fileName       : RoomServiceImpl date           :
 * 2023-04-18 description    :
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

  private final RoomCategoryRepository roomCategoryRepository;
  private final AreaRepository areaRepository;
  private final RoomRepository roomRepository;
  private final UserRepository userRepository;


  /**
   * 방 생성
   * @param request
   * @return
   */
  @Override
  @Transactional
  public CreateRoomResponse CreateRoom(CreateRoomRequest request) {

    // 방, 지역 카테고리 객체화
    RoomCategory roomCategory = roomCategoryRepository.findByCategory(request.getRoomCategory());
    Area area = areaRepository.findByCategory(request.getArea());
//  private final JPAQueryFactory queryFactory;

    // request dto를 저장
    Room room = Room.builder()
            .title(request.getTitle())
            .headCount(request.getHeadCount())
            .roomCategory(roomCategory)
            .area(area)
            .build();

    roomRepository.save(room);


    return null;
  }

  @Override
  public List<Room> findAllByArea(String areaString) {
//    return roomRepository.findAllByArea(area)
//        .orElseThrow(
//            () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM_LIST));
    return null;
  }

  /**
   * desc: 요청한 방 한개에 대한 상세 정보 조회
   * @param roomId 찾으려는 방 Id (pathvariable)
   * @return 요청한 방에 대한 상세 정보
   */
  @Override
  public FindRoomResponse findRoom(Long roomId) {
    Optional<Room> room = roomRepository.findById(roomId);
    Room findRoom = room.orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM));

    Optional<List<User>> userList = userRepository.findByRoom(findRoom);
    List<User> findUserList = userList.orElseThrow(() -> new IllegalStateException("유저가 없는 방은 존재할 수 없습니다."));



  }

}
