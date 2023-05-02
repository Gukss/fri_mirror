package com.project.fri.gameRoom.controller;

import com.project.fri.common.entity.Category;
import com.project.fri.gameRoom.dto.FindAllGameRoomResponse;
import com.project.fri.gameRoom.dto.FindGameRoomResponse;
import com.project.fri.gameRoom.service.GameRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 게임 방 더보기
     * @param page
     * @return
     */
    @GetMapping
    public ResponseEntity<List<FindAllGameRoomResponse>> findAllGameRoom(
            @RequestParam("area") Category area,
            @RequestParam("page") int page) {
        List<FindAllGameRoomResponse> allGameRoom = gameRoomService.findAllGameRoom(area, page, 20);
        return ResponseEntity.status(200).body(allGameRoom);
    }
}
