package com.project.fri.gameChatting.service;


import com.project.fri.chatting.dto.CreateChattingMessageRequest;
import com.project.fri.gameChatting.dto.CreateGameChattingMessageRequest;
import com.project.fri.gameChatting.dto.FindGameChattingMessageResponse;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface GameChattingService {
  void createGameChatting(CreateGameChattingMessageRequest request,Long userId);
  List<FindGameChattingMessageResponse> findGameChatting(Long roomId);
}
