package com.project.fri.gameRoom.controller;

import com.project.fri.common.entity.Category;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.gameRoom.dto.CreateGameRoomRequest;
import com.project.fri.gameRoom.dto.CreateGameRoomResponse;
import com.project.fri.gameRoom.dto.FindAllGameRoomResponse;
import com.project.fri.gameRoom.dto.FindGameRoomResponse;
import com.project.fri.gameRoom.service.GameRoomService;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private final UserRepository userRepository;

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

    /**
     * 게임 방 생성
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<CreateGameRoomResponse> createGameRoom(@RequestBody @Valid CreateGameRoomRequest request) {

        // todo: header에 담긴 userId로 교체
        Long userId = 2l;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionMessage(
                        NotFoundExceptionMessage.NOT_FOUND_USER
                ));

        CreateGameRoomResponse createGameRoom = gameRoomService.createGameRoom(request, user);
        return ResponseEntity.status(201).body(createGameRoom);
    }
}
