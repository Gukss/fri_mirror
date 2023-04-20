package com.project.fri.room.service;

import static com.project.fri.room.entity.Category.BETTING;

import com.project.fri.common.entity.Area;
import com.project.fri.common.entity.Category;
import com.project.fri.common.repository.AreaRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.room.dto.CreateRoomRequest;
import com.project.fri.room.dto.CreateRoomResponse;
import com.project.fri.room.dto.FindAllRoomByCategoryResponse;
import com.project.fri.room.entity.Room;
import com.project.fri.room.entity.RoomCategory;
import com.project.fri.room.repository.RoomCategoryRepository;
import com.project.fri.room.repository.RoomRepository;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import com.project.fri.util.BaseEntity;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

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

    // headCount 내기 방 제외 DB 저장 전에 x2
    int headCount = request.getHeadCount();
    if (roomCategory.getCategory() != BETTING) {
      headCount *= 2;
    }

    // request dto를 저장
    Room room = Room.builder()
        .title(request.getTitle())
        .headCount(headCount)
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


  /**
   * desc: 방 더보기
   * @param stringArea
   * @param stringCategory
   * @return
   */
  @Override
  public List<FindAllRoomByCategoryResponse> findAllByAreaAndRoomCategory(Category stringArea, com.project.fri.room.entity.Category stringCategory) {

    // enum 타입으로 객체를 찾음
    Area area = areaRepository.findByCategory(stringArea);
    RoomCategory roomCategory = roomCategoryRepository.findByCategory(stringCategory);

    // 지역과 카테고리로 방 목록을 찾음
    List<Room> findAllRoom = roomRepository.findAllByAreaAndRoomCategory(area,
        roomCategory).orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM_LIST));

    // 찾은 방 목록으로 user찾아서 전공, 비전공자 참여자 수 추가해서 dto로 반환하기
    List<FindAllRoomByCategoryResponse> seeMoreRoom = findAllRoom.stream()
        .map(findRoom -> {

          // 방으로 참여 user 찾기
          List<User> users = userRepository.findAllByRoom(findRoom).orElseThrow(
              () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
          long major = users.stream().filter(user -> user.isMajor() == true).count();    // 전공자
          long nonMajor = users.size() - major;                                          // 비전공자

          // response dto로 반환
          FindAllRoomByCategoryResponse findAllroom = FindAllRoomByCategoryResponse.create(
              findRoom, major, nonMajor);
          return findAllroom;
        }).collect(Collectors.toList());

    return seeMoreRoom;
  }


}
