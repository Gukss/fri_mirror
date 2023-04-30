package com.project.fri.gameRoom.controller;

import com.project.fri.gameRoom.dto.FindGameRoomResponse;
import com.project.fri.gameRoom.service.GameRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.project.fri.gameRoom.controller fileName       : GameRoomController author
 *       : hagnoykmik date           : 2023-04-25 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-04-25        hagnoykmik       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/game-room")
@Slf4j
public class GameRoomController {

    private final GameRoomService gameRoomService;

    /**
     * 게임 방 정보조회
     * @param gameRoomId
     * @return
     */
    @GetMapping("/{gameRoomId}")
    public ResponseEntity<FindGameRoomResponse> findGameRoom(@PathVariable("gameRoomId") Long gameRoomId) {
        FindGameRoomResponse gameRoom = gameRoomService.findGameRoom(gameRoomId);
        return ResponseEntity.status(200).body(gameRoom);
    }
}
