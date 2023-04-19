package com.project.fri.room.controller;

import java.util.Arrays;
import com.project.fri.room.entity.Room;
import com.project.fri.room.service.RoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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



  @PostMapping("/room")
  public ResponseEntity<CreateRoomResponse> createRoom(CreateRoomRequest request) {
    CreateRoomResponse createRoomResponse = roomService.CreateRoom(request);
    return ResponseEntity.status(201).body(createRoomResponse);
  }
}
