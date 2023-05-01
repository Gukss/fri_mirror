package com.project.fri.room.service;

import com.project.fri.common.entity.Area;
import com.project.fri.common.entity.Category;
import com.project.fri.common.repository.AreaRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.room.dto.CreateRoomRequest;
import com.project.fri.room.dto.CreateRoomResponse;
import com.project.fri.room.dto.FindAllRoomByCategoryResponse;
import com.project.fri.room.dto.FindAllUserByRoomIdDto;
import com.project.fri.room.dto.FindRoomResponse;
import com.project.fri.room.dto.FindAllRoomInstance;
import com.project.fri.room.dto.FindAllRoomResponse;
import com.project.fri.room.entity.Room;
import com.project.fri.room.entity.RoomCategory;
import com.project.fri.room.repository.RoomCategoryRepository;
import com.project.fri.room.repository.RoomRepository;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import java.util.ArrayList;
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
    RoomCategory roomCategory = roomCategoryRepository.findByCategory(request.getRoomCategory())
        .orElseThrow(
            () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM_CATEGORY));
    Area area = areaRepository.findByCategory(request.getArea())
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_AREA));

    // headCount 내기 방 제외 DB 저장 전에 x2
    int headCount = request.getHeadCount();
    // if (roomCategory.getCategory() != BETTING) {
    //   headCount *= 2;
    // }

    // request dto를 저장
    Room room = Room.create(request, findUser, headCount, roomCategory, area);
    roomRepository.save(room);

    // todo: user테이블에 방번호 추가(update)
    findUser.updateRoomNumber(room);

    // response dto로 변환
    CreateRoomResponse createRoomResponse = CreateRoomResponse.create(room, findUser);

    return createRoomResponse;
  }

  @Override
  public List<Room> findAllByArea(String areaString) {
    return null;
  }

  @Override
  public FindAllRoomResponse findAllByArea(Category areaString) {
    Area foundArea = areaRepository.findByCategory(areaString)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_AREA));

    //todo: login 완료되면 id 말고 다른 값으로 찾을꺼같다. => 바꿔주기
    User foundUser = userRepository.findById(1L)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
    Long enrollRoomId = foundUser.getRoom().getId();

    //이 roomList에서 해당 방으로 userList를 찾아서 size()를 찾아서 가득찬 방 없애야한다.
    //user마다 전공 여부도 체크해줘야한다.
    List<Room> roomList = roomRepository.findAllByAreaOrderByCreatedAtDesc(foundArea);

    //roomList 반복하면서 responseEnitity 채워주기
    List<FindAllRoomInstance> drinkList = new ArrayList<>();
    List<FindAllRoomInstance> mealList = new ArrayList<>();
    List<FindAllRoomInstance> gameList = new ArrayList<>();
    List<FindAllRoomInstance> exerciseList = new ArrayList<>();
    List<FindAllRoomInstance> studyList = new ArrayList<>();
    List<FindAllRoomInstance> bettingList = new ArrayList<>();
    List<FindAllRoomInstance> etcList = new ArrayList<>();

    for (Room x : roomList) {
      com.project.fri.room.entity.Category category = x.getRoomCategory().getCategory();
      List<User> foundUserList = userRepository.findAllByRoom(x);
      int size = foundUserList.size(); //방에 참여한 인원수
      int headCount = x.getHeadCount(); //방 최대 인원 => 위에 참여 인원수와 비교해서 가득찬 방인지 판별한다.
      if (size == headCount) { //가득찬 방이면 continue;
        continue;
      }
      int majorCount = 0;
      int nonMajorCount = 0;

      for (User u : foundUserList) {
        boolean major = u.isMajor();
        if (major) {
          majorCount += 1;
        } else {
          nonMajorCount += 1;
        }
      }

      if (x.getId() == enrollRoomId) { //참여중인 방이면 화면에 출력되지 않는다.
        continue;
      }
      switch (category) {
        //총 7개 + etc
        case DRINK:
          if (drinkList.size() < 10) {
            drinkList.add(x.createFindRoomResponse(category, majorCount, nonMajorCount));
          }
          break;
        case MEAL:
          if (mealList.size() < 10) {
            mealList.add(x.createFindRoomResponse(category, majorCount, nonMajorCount));
          }
          break;
        case GAME:
          if (gameList.size() < 10) {
            gameList.add(x.createFindRoomResponse(category, majorCount, nonMajorCount));
          }
          break;
        case EXERCISE:
          if (exerciseList.size() < 10) {
            exerciseList.add(x.createFindRoomResponse(category, majorCount, nonMajorCount));
          }
          break;
        case STUDY:
          if (studyList.size() < 10) {
            studyList.add(x.createFindRoomResponse(category, majorCount, nonMajorCount));
          }
          break;
        case ETC:
          if (etcList.size() < 10) {
            etcList.add(x.createFindRoomResponse(category, majorCount, nonMajorCount));
          }
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

  /**
   * desc: 요청한 방 한개에 대한 상세 정보 조회
   *
   * @param roomId 찾으려는 방 Id (pathvariable)
   * @return 요청한 방에 대한 상세 정보
   */
  @Override
  public FindRoomResponse findRoom(Long roomId, Long userId) {
    //정보 조회할 방 가져오기
    Optional<Room> room = roomRepository.findRoomWithCategoryById(roomId);
    Room findRoom = room.orElseThrow(
        () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM));

    //가져온 방에 속해있는 userList 가져오기
    List<User> findUserList = userRepository.findAllByRoom(findRoom);

    //전공인 사람 list로 묶기
    List<FindAllUserByRoomIdDto> majorList = findUserList.stream()
        .filter(User::isMajor)
        .map(user -> new FindAllUserByRoomIdDto(user.getName(), user.getProfileUrl()))
        .collect(Collectors.toList());

    //비전공인 사람 list로 묶깅
    List<FindAllUserByRoomIdDto> nonMajorList = findUserList.stream()
        .filter(user -> !user.isMajor())
        .map(user -> new FindAllUserByRoomIdDto(user.getName(), user.getProfileUrl()))
        .collect(Collectors.toList());

    //본인 객체 가지고 오기
    Optional<User> user = userRepository.findById(userId);
    User findUser = user.orElseThrow(
        () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    //참여 여부 판별
    Boolean isParticipated = findUser.getRoom().equals(findRoom);

    return new FindRoomResponse(findRoom, isParticipated, majorList, nonMajorList);
  }


  /**
   * desc: 방 더보기
   *
   * @param stringArea
   * @param stringCategory
   * @return
   */
  @Override
  public List<FindAllRoomByCategoryResponse> findAllByAreaAndRoomCategory(Category stringArea,
      com.project.fri.room.entity.Category stringCategory) {

    // enum 타입으로 객체를 찾음
    Area area = areaRepository.findByCategory(stringArea)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_AREA));
    RoomCategory roomCategory = roomCategoryRepository.findByCategory(stringCategory)
        .orElseThrow(
            () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM_CATEGORY));

    // 지역과 카테고리로 방 목록을 찾음
    List<Room> findAllRoom = roomRepository.findAllByAreaAndRoomCategory(area,
        roomCategory);

    // 찾은 방 목록으로 user찾아서 전공, 비전공자 참여자 수 추가해서 dto로 반환하기
    List<FindAllRoomByCategoryResponse> seeMoreRoom = findAllRoom.stream()
        .map(findRoom -> {

          // 방으로 참여 user 찾기
          List<User> users = userRepository.findAllByRoom(findRoom);
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
