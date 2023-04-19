package com.project.fri.room.service;

import com.project.fri.common.entity.Area;
import com.project.fri.common.repository.AreaRepository;
import com.project.fri.room.dto.CreateRoomRequest;
import com.project.fri.room.dto.CreateRoomResponse;
import com.project.fri.room.entity.Room;
import com.project.fri.room.entity.RoomCategory;
import com.project.fri.room.repository.RoomCategoryRepository;
import com.project.fri.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.project.fri.room.service fileName       : RoomServiceImpl date           :
 * 2023-04-18 description    :
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

  private final RoomCategoryRepository roomCategoryRepository;
  private final AreaRepository areaRepository;
  private final RoomRepository roomRepository;


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

    // request dto를 저장
    Room room = Room.builder()
        .title(request.getTitle())
        .headCount(request.getHeadCount())
        .roomCategory(roomCategory)
        .area(area)
        .build();

    Room saveRoom = roomRepository.save(room);

    // user 찾기

    // response dto로 변환
    CreateRoomResponse createRoomResponse = CreateRoomResponse.create(saveRoom);

    return createRoomResponse;
  }


}
