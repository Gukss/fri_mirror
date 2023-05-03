package com.project.fri.gameChatting.controller;

import com.project.fri.gameChatting.dto.CreateGameChattingMessageRequest;
import com.project.fri.gameChatting.dto.FindGameChattingMessageResponse;
import com.project.fri.gameChatting.service.GameChattingServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gameChatting")
@Slf4j
public class GameChattingController {
  private final GameChattingServiceImpl gameChattingService;
  @PostMapping()
  public ResponseEntity<?> createGameChatting(@RequestBody CreateGameChattingMessageRequest request, @RequestHeader("Authorization") Long userId){
    gameChattingService.createGameChatting(request,userId);
    return ResponseEntity.ok("ok");
  }


  @GetMapping("/{roomId}")
  public ResponseEntity<List<FindGameChattingMessageResponse>> findGameChattingList(@PathVariable Long roomId){
    List<FindGameChattingMessageResponse> responseList = gameChattingService.findGameChatting(roomId);
    return ResponseEntity.ok(responseList);
  }
}
