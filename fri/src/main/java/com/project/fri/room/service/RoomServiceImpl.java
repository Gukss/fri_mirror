package com.project.fri.room.service;

import com.project.fri.common.entity.Area;
import com.project.fri.common.entity.Category;
import com.project.fri.common.repository.AreaRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.room.dto.CreateRoomRequest;
import com.project.fri.room.dto.CreateRoomResponse;
import com.project.fri.room.dto.FindAllRoomInstance;
import com.project.fri.room.dto.FindAllRoomResponse;
import com.project.fri.room.entity.Room;
import com.project.fri.room.entity.RoomCategory;
import com.project.fri.room.repository.RoomCategoryRepository;
import com.project.fri.room.repository.RoomRepository;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import com.project.fri.util.BaseEntity;
import java.util.ArrayList;
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
   *
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
  public FindAllRoomResponse findAllByArea(Category areaString) {
    Area foundArea = areaRepository.findByCategory(areaString);

    //todo: login 완료되면 id 말고 다른 값으로 찾을꺼같다. => 바꿔주기
    User foundUser = userRepository.findById(1L)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
    Long enrollRoomId = foundUser.getRoom().getId();

    List<Room> roomList = roomRepository.findAllByArea(foundArea)
        .orElseThrow(
            () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM_LIST));

    //roomList 반복하면서 responseEnitity 채워주기
    List<FindAllRoomInstance> drinkList = new ArrayList<>();
    List<FindAllRoomInstance> mealList = new ArrayList<>();
    List<FindAllRoomInstance> gameList = new ArrayList<>();
    List<FindAllRoomInstance> exerciseList = new ArrayList<>();
    List<FindAllRoomInstance> studyList = new ArrayList<>();
    List<FindAllRoomInstance> bettingList = new ArrayList<>();
    List<FindAllRoomInstance> etcList = new ArrayList<>();

    for(Room x: roomList){

      com.project.fri.room.entity.Category category = x.getRoomCategory().getCategory();
      if(x.getId() == enrollRoomId){ //참여중인 방이면 화면에 출력되지 않는다.
        continue;
      }
      switch(category){
        //총 7개 + etc
        case DRINK:
          drinkList.add(x.createFindRoomResponse(category));
          break;
        case MEAL:
          mealList.add(x.createFindRoomResponse(category));
          break;
        case GAME:
          gameList.add(x.createFindRoomResponse(category));
          break;
        case EXERCISE:
          exerciseList.add(x.createFindRoomResponse(category));
          break;
        case STUDY:
          studyList.add(x.createFindRoomResponse(category));
          break;
        case BETTING:
          bettingList.add(x.createFindRoomResponse(category));
          break;
        case ETC:
          etcList.add(x.createFindRoomResponse(category));
          break;
        default:
          //todo: 카테고리 없는거 어떻게 할 지 정하기 => 카테고리 없는게 들어올리가 없긴하다. => exceptino으로 던지면 될까?
          break;
      }
    }

    //for문 탈출하면 각 리스트에 값이 다 담겨있다는 의미다.
    //todo: FindAllRoomResponse에 담아서 ResponseEntity만들어주기
    return FindAllRoomResponse.builder()
        .betting(bettingList)
        .meal(mealList)
        .stydy(studyList)
        .etc(etcList)
        .drink(drinkList)
        .game(gameList)
        .exercise(exerciseList)
        .build();
  }
}
