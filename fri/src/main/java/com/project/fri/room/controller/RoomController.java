package com.project.fri.room.controller;

import java.util.Arrays;

import com.project.fri.room.dto.FindRoomResponse;
import com.project.fri.room.entity.Room;
import com.project.fri.room.service.RoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping("/{roomId}")
  public ResponseEntity<FindRoomResponse> findRoom(@PathVariable("roomId") Long roomId) {
    Long userId = 4L;
    FindRoomResponse result = roomService.findRoom(roomId, userId);
    return ResponseEntity.status(200).body(result);
  }


}
