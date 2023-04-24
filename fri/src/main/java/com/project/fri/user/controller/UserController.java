package com.project.fri.user.controller;

import com.project.fri.user.dto.UpdateUserRoomRequest;
import com.project.fri.user.dto.UpdateUserRoomResponse;
import com.project.fri.user.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.project.fri.user.dto.UpdateUserReadyResponse;
import com.project.fri.user.service.UserService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.project.fri.user.controller fileName       : UserController date           :
 * 2023-04-18 description    :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j

public class UserController {
    private final UserService userService;

    @PatchMapping("/room/{roomId}")
    public ResponseEntity<UpdateUserRoomResponse> updateUserRoom(@PathVariable("roomId") Long roomId, @RequestBody UpdateUserRoomRequest request) {
        Long userId = 4L;
        UpdateUserRoomResponse updateUserRoomResponse = userService.updateUserRoom(roomId, request, userId);
        return ResponseEntity.status(201).body(updateUserRoomResponse);
    }

  @PatchMapping("{roomId}/ready")
  public ResponseEntity<UpdateUserReadyResponse> updateUserReady(@PathVariable Long roomId) {
    //todo: userId는 토큰에서 가지고 와서 넣어주기(updateUserReady에서)
    UpdateUserReadyResponse updateUserReadyResponse = userService.updateUserReady(1L, roomId);
    URI uri = URI.create(roomId+"/ready");
    return ResponseEntity.created(uri).body(updateUserReadyResponse);
  }
}
