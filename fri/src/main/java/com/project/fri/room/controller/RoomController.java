package com.project.fri.room.controller;

import com.project.fri.common.entity.Category;
import com.project.fri.room.dto.FindAllRoomInstance;
import com.project.fri.room.dto.FindAllRoomResponse;
import com.project.fri.room.dto.FindRoomResponse;
import com.project.fri.room.entity.RoomCategory;
import com.project.fri.user.entity.User;
import com.project.fri.user.service.UserService;
import java.util.ArrayList;
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
  private final UserService userService;
  @GetMapping
  public ResponseEntity<FindAllRoomResponse> findAllByArea(@RequestParam("area") String stringArea){
    //String 값 Enum으로 바꿔서 roomList 찾기
    Category area = Category.valueOf(stringArea);
    FindAllRoomResponse findAllRoomResponse = roomService.findAllByArea(area);
    return ResponseEntity.ok().body(findAllRoomResponse);
  }

//    @GetMapping("/room/{roomId}")
//    public

}
