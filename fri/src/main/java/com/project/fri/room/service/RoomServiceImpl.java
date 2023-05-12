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
import com.project.fri.user.service.UserRoomTimeService;
import com.project.fri.user.service.UserRoomTimeServiceImpl;
import com.sun.jdi.request.InvalidRequestStateException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
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
  private final UserRoomTimeService userRoomTimeService;

  /**
   * 방 생성
   *
   * @param request
   * @return 만든 방 제목
   */
  @Override
  @Transactional
  public CreateRoomResponse createRoom(CreateRoomRequest request, Long userId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(
            NotFoundExceptionMessage.NOT_FOUND_USER));

    // 만약 다른 방에 참여하고 있거나, 하트가 1개 미만이면 방생성할 수 없다
    if (user.getRoom() != null || user.getHeart() < 1) {
      throw new InvalidRequestStateException("방을 생성할 수 없습니다.");
    }

    // 방, 지역 카테고리 객체화
    RoomCategory roomCategory = roomCategoryRepository.findByCategory(request.getRoomCategory())
        .orElseThrow(
            () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM_CATEGORY));
    Area area = areaRepository.findByCategory(request.getArea())
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_AREA));

    // request dto를 저장
    Room room = Room.create(request, user, roomCategory, area);

    Room save = roomRepository.save(room);

    HttpStatus userRoomTime = userRoomTimeService.createUserRoomTime(user, save);
    // userRoomTime 테이블에서 에러 발생시 예외 에러처리
    if (userRoomTime.isError()){
      throw new InvalidRequestStateException();
    }

    // user 테이블에 방번호 추가
    user.updateRoomNumber(room);

    // 방에 들어가면서 하트 1개 소진
    user.minusHeart();

    // response dto로 변환
    CreateRoomResponse createRoomResponse = CreateRoomResponse.create(room);

    return createRoomResponse;
  }

  @Override
  public FindAllRoomResponse findAllByArea(Category areaString, Long userId) {
    Area foundArea = areaRepository.findByCategory(areaString)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_AREA));

    User foundUser = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    Long enrollRoomId = null;
    if (foundUser.getRoom() != null) {
      enrollRoomId = foundUser.getRoom().getId();
    }

    //이 roomList에서 해당 방으로 userList를 찾아서 size()를 찾아서 가득찬 방 없애야한다.
    //user마다 전공 여부도 체크해줘야한다.is_delete 컬럼 값이 false인 경우만 들고온다.
    List<Room> roomList = roomRepository.findAllByAreaAndIsDeleteFalseOrderByCreatedAtDesc(foundArea);

    //roomList 반복하면서 responseEnitity 채워주기
    List<FindAllRoomInstance> drinkList = new ArrayList<>();
    List<FindAllRoomInstance> mealList = new ArrayList<>();
    List<FindAllRoomInstance> gameList = new ArrayList<>();
    List<FindAllRoomInstance> exerciseList = new ArrayList<>();
    List<FindAllRoomInstance> studyList = new ArrayList<>();
    List<FindAllRoomInstance> bettingList = new ArrayList<>();
    List<FindAllRoomInstance> etcList = new ArrayList<>();

    boolean fullDrinkList = false;
    boolean fullMealList = false;
    boolean fullGameList = false;
    boolean fullExerciseList = false;
    boolean fullStudyList = false;
    boolean fullBettingList = false;
    boolean fullEtcList = false;

    for (Room x : roomList) {
      if (fullDrinkList && fullMealList && fullGameList && fullExerciseList && fullStudyList
          && fullBettingList && fullEtcList) { //모든 리스트가 가득 찼으면 더이상 반복안해도 된다.
        break;
      }
      if (x.isDelete() == true) {
        continue;
      }
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

      if (enrollRoomId != null && x.getId() == enrollRoomId) { //참여중인 방이면 화면에 출력되지 않는다.
        continue;
      }
      switch (category) {
        //총 7개 + etc
        case DRINK:
          if (!fullDrinkList && drinkList.size() < 10) {
            drinkList.add(x.createFindRoomResponse(category, majorCount, nonMajorCount));
          }else{
            fullDrinkList = true;
          }
          break;
        case MEAL:
          if (!fullMealList && mealList.size() < 10) {
            mealList.add(x.createFindRoomResponse(category, majorCount, nonMajorCount));
          }else{
            fullMealList = true;
          }
          break;
        case GAME:
          if (!fullGameList && gameList.size() < 10) {
            gameList.add(x.createFindRoomResponse(category, majorCount, nonMajorCount));
          }else{
            fullGameList = true;
          }
          break;
        case EXERCISE:
          if (!fullExerciseList && exerciseList.size() < 10) {
            exerciseList.add(x.createFindRoomResponse(category, majorCount, nonMajorCount));
          }else{
            fullExerciseList = true;
          }
          break;
        case STUDY:
          if (!fullStudyList && studyList.size() < 10) {
            studyList.add(x.createFindRoomResponse(category, majorCount, nonMajorCount));
          }else{
            fullStudyList = true;
          }
          break;
        case ETC:
          if (!fullEtcList && etcList.size() < 10) {
            etcList.add(x.createFindRoomResponse(category, majorCount, nonMajorCount));
          }else{
            fullEtcList = true;
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
    Room findRoom = roomRepository.findRoomWithCategoryById(roomId).orElseThrow(
        () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM));

    //가져온 방에 속해있는 userList 가져오기
    List<User> findUserList = userRepository.findAllByRoom(findRoom);

    //전공인 사람 list로 묶기
    List<FindAllUserByRoomIdDto> majorList = findUserList.stream()
        .filter(User::isMajor)
        .map(FindAllUserByRoomIdDto::new)
        .collect(Collectors.toList());

    //비전공인 사람 list로 묶깅
    List<FindAllUserByRoomIdDto> nonMajorList = findUserList.stream()
        .filter(user -> !user.isMajor())
        .map(FindAllUserByRoomIdDto::new)
        .collect(Collectors.toList());

    //본인 객체 가지고 오기
    Optional<User> user = userRepository.findById(userId);
    User findUser = user.orElseThrow(
        () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    //참여 여부 판별
    boolean isParticipated = false;
    if (findUser.getRoom() != null) { //방에 참여하지 않으면 기본 false로 반환
      isParticipated = findUser.getRoom().equals(findRoom);
    }

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
      com.project.fri.room.entity.Category stringCategory, int page, Pageable pageable, Long userId) {

    Pageable newPageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());

    // enum 타입으로 객체를 찾음
    Area area = areaRepository.findByCategory(stringArea)
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_AREA));
    RoomCategory roomCategory = roomCategoryRepository.findByCategory(stringCategory)
        .orElseThrow(
            () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM_CATEGORY));

    // todo : user테이블과 room테이블을 join해서 가져올 수 있을거 같음 (이렇게 바꾸면 모든 값을 가져와서 map을 돌리지 않고 db에서 가져올때부터 필요한 값만 가져올 수 있음)
    // 지역과 카테고리로 방 목록을 찾음 이때 is_delete가 false일 때문 불러오기
    List<Room> findAllRoom = roomRepository.findAllByAreaAndRoomCategoryAndIsDeleteFalse(area,
            roomCategory, newPageable);

    // 방 리스트에서 내가 참여한 방 안보이게 하기
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(
            NotFoundExceptionMessage.NOT_FOUND_USER
        ));

    // 방 리스트에 user가 참여한 방이있으면 제거한다.
    // list.contains()로 확인안해도 remove()에서 알아서 검사하고 있으면 삭제해줌
    findAllRoom.remove(user.getRoom());

    // 찾은 방 목록으로 user찾아서 전공, 비전공자 참여자 수 추가해서 dto로 반환하기
    List<FindAllRoomByCategoryResponse> seeMoreRoom = findAllRoom.stream()
        .map(findRoom -> {

          // 방으로 참여 user 찾기
          List<User> users = userRepository.findAllByRoom(findRoom);
          long major = users.stream().filter(u -> u.isMajor() == true).count();    // 전공자
          long nonMajor = users.size() - major;                                          // 비전공자

          // response dto로 반환
          FindAllRoomByCategoryResponse findAllroom = FindAllRoomByCategoryResponse.create(
              findRoom, major, nonMajor);
          return findAllroom;
        }).collect(Collectors.toList());

    return seeMoreRoom;
  }

  /*
  매주 금요일 23시 11분 모든방 삭제처리
   */
  @Override
  @Scheduled(cron = "0 11 23 ? * FRI")
  @Transactional
  public void deleteRoomScheduler() {
    List<Room> allRoomList = roomRepository.findAll();
    for (Room r : allRoomList) {
      r.deleteRoom();
    }
  }

}
