package com.project.fri.room.controller;

import com.project.fri.common.entity.Area;
import com.project.fri.common.entity.Category;
import com.project.fri.room.dto.FindAllRoomByCategoryResponse;
import com.project.fri.room.entity.RoomCategory;
import java.util.Arrays;
import com.project.fri.room.entity.Room;
import com.project.fri.room.service.RoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.fri.room.dto.CreateRoomRequest;
import com.project.fri.room.dto.CreateRoomResponse;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * packageName    : com.project.fri.room.controller fileName       : RoomController date           :
 * 2023-04-18 description    :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
@Slf4j
public class RoomController {

  private final RoomService roomService;

  @GetMapping
  public ResponseEntity<?> findAllByArea(@RequestParam String area){


    List<Room> roomList = roomService.findAllByArea(area);
    log.debug("roomList = " + Arrays.toString(roomList.toArray()));
    return null;
  }

//    @GetMapping("/room/{roomId}")
//    public


  /**
   * 방 생성
   */
  @PostMapping
  public ResponseEntity<CreateRoomResponse> createRoom(@RequestBody @Validated CreateRoomRequest request) {
    CreateRoomResponse createRoomResponse = roomService.createRoom(request);
    return ResponseEntity.status(201).body(createRoomResponse);
  }

  /**
   * 방 더보기
   * @param area
   * @param category
   * @return
   */
  @GetMapping("/category")
  public ResponseEntity<List<FindAllRoomByCategoryResponse>> findAllByAreaAndRoomCategory(
      @RequestParam("area") Category area,
      @RequestParam("category") com.project.fri.room.entity.Category category) {

    List<FindAllRoomByCategoryResponse> seeMoreRoom = roomService.findAllByAreaAndRoomCategory(
        area, category);
    return ResponseEntity.status(200).body(seeMoreRoom);
  }


}
