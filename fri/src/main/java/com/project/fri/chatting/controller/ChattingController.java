package com.project.fri.chatting.controller;

import com.project.fri.chatting.dto.CreateChattingMessageRequest;
import com.project.fri.chatting.dto.FindChattingMessageResponse;
import com.project.fri.chatting.dto.SocketChattingRequestAndResponse;
import com.project.fri.chatting.service.ChattingServiceImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
@Slf4j
public class ChattingController {
  private final ChattingServiceImpl chattingService;
  private final SimpMessageSendingOperations messagingTemplate;

  @MessageMapping("/chatting")
  public void message(SocketChattingRequestAndResponse message){
    // sub한 주소에 chatMessage객체 전달
    messagingTemplate.convertAndSend("/sub/room/" + message.getRoomId(), message);
  }

  @PostMapping()
  public ResponseEntity<?> createChatting(@RequestBody CreateChattingMessageRequest request, @RequestHeader("Authorization") Long userId){
    chattingService.createChatting(request,userId);
    return ResponseEntity.ok("ok");
  }


  @GetMapping("/{roomId}")
  public ResponseEntity<List<FindChattingMessageResponse>> findChattingList(@PathVariable Long roomId){
    List<FindChattingMessageResponse> responseList = chattingService.findChatting(roomId);
    return ResponseEntity.ok(responseList);
  }
}
