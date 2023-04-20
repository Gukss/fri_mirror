package com.project.fri.user.controller;

import com.project.fri.room.dto.UpdateUserRoomRequest;
import com.project.fri.room.dto.UpdateUserRoomResponse;
import com.project.fri.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.project.fri.user.controller fileName       : UserController date           :
 * 2023-04-18 description    :
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PatchMapping("/room/{roomId}")
    public ResponseEntity<UpdateUserRoomResponse> updateUserRoom(@PathVariable("roomId") String roomId, @RequestBody UpdateUserRoomRequest request) {
        UpdateUserRoomResponse updateUserRoomResponse = userService.updateUserRoom();
        return ResponseEntity.status(201).body(updateUserRoomResponse);
    }

}
