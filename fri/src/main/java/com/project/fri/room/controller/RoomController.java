package com.project.fri.room.controller;

import com.project.fri.room.dto.CreateRoomRequest;
import com.project.fri.room.dto.CreateRoomResponse;
import com.project.fri.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.project.fri.room.controller fileName       : RoomController date           :
 * 2023-04-18 description    :
 */
@RestController
@RequiredArgsConstructor
public class RoomController {

  private final RoomService roomService;

  @PostMapping("/room")
  public ResponseEntity<CreateRoomResponse> createRoom(CreateRoomRequest request) {
    CreateRoomResponse createRoomResponse = roomService.CreateRoom(request);
    return ResponseEntity.status(201).body(createRoomResponse);
  }
}
