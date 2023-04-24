package com.project.fri.room.controller;

import com.project.fri.common.entity.Area;
import com.project.fri.common.entity.Category;
import com.project.fri.room.dto.FindAllRoomByCategoryResponse;
import com.project.fri.room.entity.RoomCategory;
import java.util.Arrays;
import com.project.fri.room.dto.FindRoomResponse;
import com.project.fri.room.entity.Room;
import com.project.fri.common.entity.Category;
import com.project.fri.room.dto.FindAllRoomResponse;
import com.project.fri.room.service.RoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
  //todo: queryString을 enum으로 하면 자동으로 String이 enum으로 바뀐다. 수정해도 되고, 안해도 되고.
  public ResponseEntity<FindAllRoomResponse> findAllByArea(@RequestParam("area") String stringArea){
    //String 값 Enum으로 바꿔서 roomList 찾기
    Category area = Category.valueOf(stringArea);
    FindAllRoomResponse findAllRoomResponse = roomService.findAllByArea(area);
    return ResponseEntity.ok().body(findAllRoomResponse);
  }

  @GetMapping("/{roomId}")
  public ResponseEntity<FindRoomResponse> findRoom(@PathVariable("roomId") Long roomId) {
    Long userId = 4L;
    FindRoomResponse result = roomService.findRoom(roomId, userId);
    return ResponseEntity.status(200).body(result);
  }

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
