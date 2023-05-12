package com.project.fri.gameRoom.controller;

import com.project.fri.gameRoom.dto.SocketGameRoomStopRequestAndResponse;
import com.project.fri.common.entity.Category;
import com.project.fri.gameRoom.dto.*;
import com.project.fri.gameRoom.service.GameRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * packageName    : com.project.fri.gameRoom.controller fileName       : GameRoomController author :
 * hagnoykmik date           : 2023-04-25 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2023-04-25        hagnoykmik
 * 최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/game-room")
@Slf4j
public class GameRoomController {

  private final GameRoomService gameRoomService;
  private final SimpMessageSendingOperations messagingTemplate;


  /**
   * 게임 방 정보조회
   *
   * @param gameRoomId
   * @return
   */
  @GetMapping("/{gameRoomId}")
  public ResponseEntity<FindGameRoomResponse> findGameRoom(
      @PathVariable("gameRoomId") Long gameRoomId,
      @RequestHeader("Authorization") Long userId) {

    FindGameRoomResponse gameRoom = gameRoomService.findGameRoom(gameRoomId, userId);
    return ResponseEntity.status(200).body(gameRoom);
  }

  /**
   * 게임 방 더보기
   *
   * @param page
   * @return
   */
  @GetMapping("/category")
  public ResponseEntity<List<FindAllGameRoomResponse>> findAllGameRoom(
      @RequestParam("area") Category area,
      @RequestParam("page") int page,
      @PageableDefault(size = 20, sort = "createdAt", direction = Direction.DESC) Pageable pageable,
      @RequestHeader("Authorization") Long userId) {
    List<FindAllGameRoomResponse> allGameRoom = gameRoomService.findAllGameRoom(userId, area, page,
        pageable);
    return ResponseEntity.status(200).body(allGameRoom);
  }

  /**
   * 게임 방 생성
   *
   * @param request
   * @return
   */
  @PostMapping
  public ResponseEntity<CreateGameRoomResponse> createGameRoom(
      @RequestBody @Valid CreateGameRoomRequest request,
      @RequestHeader("Authorization") Long userId) {

    CreateGameRoomResponse createGameRoom = gameRoomService.createGameRoom(request, userId);
    return ResponseEntity.status(201).body(createGameRoom);
  }

  /**
   * 게임 방 참여하기, 나가기
   *
   * @param gameRoomId
   * @param request
   * @return
   */
  @PatchMapping("/{gameRoomId}/participation")
  public ResponseEntity<UpdateGameRoomParticipationResponse> updateGameRoomParticipation(
      @PathVariable("gameRoomId") Long gameRoomId,
      @RequestBody UpdateGameRoomParticipationRequest request,
      @RequestHeader("Authorization") Long userId) {

    UpdateGameRoomParticipationResponse updateGameRoomParticipation = gameRoomService.updateGameRoomParticipation(
        gameRoomId, request, userId);
    if (updateGameRoomParticipation == null) {
      return ResponseEntity.unprocessableEntity().build(); // todo: 클라이언트 요청이 유효하지 않은 경우
    } else {
      return ResponseEntity.status(201).body(updateGameRoomParticipation);
    }

  }

  /**
   * 게임방 목록조회
   *
   * @param areaCategory
   * @return
   */
  @GetMapping
  public ResponseEntity<FindGameRoomListResponse> findGameRoomList(
      @RequestParam("area") Category areaCategory,
      @RequestHeader("Authorization") Long userId) {
    List<FindGameRoomInstance> gameRoomList = gameRoomService.findGameRoomList(areaCategory, userId);
    FindGameRoomListResponse result = new FindGameRoomListResponse(gameRoomList);

    return ResponseEntity.ok().body(result);
  }

  @MessageMapping("/game-room/info")
  public void message(SocketGameRoomStatusRequestAndResponse message) {
    messagingTemplate.convertAndSend("/sub/game-room/info/" + message.getGameRoomId(), message);
  }

  @MessageMapping("/game-room/stop")
  public void message(SocketGameRoomStopRequestAndResponse stop){
      messagingTemplate.convertAndSend("/sub/game-room/stop/" + stop.getGameRoomId(), stop);
  }
}
