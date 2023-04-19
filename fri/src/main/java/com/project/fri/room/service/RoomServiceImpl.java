package com.project.fri.room.service;

import com.project.fri.common.entity.Area;
import com.project.fri.common.repository.AreaRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.room.dto.CreateRoomRequest;
import com.project.fri.room.dto.CreateRoomResponse;
import com.project.fri.room.entity.Room;
import com.project.fri.room.entity.RoomCategory;
import com.project.fri.room.repository.RoomCategoryRepository;
import com.project.fri.room.repository.RoomRepository;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import com.project.fri.util.BaseEntity;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.fri.room.entity.Room;
import com.project.fri.room.repository.RoomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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
   * @return 만든 방 제목
   */
  @Override
  @Transactional
  public CreateRoomResponse createRoom(CreateRoomRequest request) {

    // todo: 가짜 user 찾기
    Long userId = 1L; // 예시로 userId를 1L로 설정합니다.
    User findUser = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(
            NotFoundExceptionMessage.NOT_FOUND_USER));

    // 방, 지역 카테고리 객체화
    RoomCategory roomCategory = roomCategoryRepository.findByCategory(request.getRoomCategory());
    Area area = areaRepository.findByCategory(request.getArea());
//  private final JPAQueryFactory queryFactory;

    // request dto를 저장
    Room room = Room.builder()
        .title(request.getTitle())
        .headCount(request.getHeadCount())
        .location(request.getLocation())
        .roomCategory(roomCategory)
        .area(area)
        // todo: user
        .baseEntity(BaseEntity.builder()
            .constructor(findUser.getName())
            .updater(findUser.getName())
            .build())
        .build();

    Room saveRoom = roomRepository.save(room);


    // todo: user테이블에 방번호 추가(update)
    findUser.updateRoomNumber(saveRoom);


    // response dto로 변환
    CreateRoomResponse createRoomResponse = CreateRoomResponse.create(saveRoom, findUser);

    return createRoomResponse;
  }

  @Override
  public List<Room> findAllByArea(String areaString) {
//    return roomRepository.findAllByArea(area)
//        .orElseThrow(
//            () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM_LIST));
    return null;
  }
}
