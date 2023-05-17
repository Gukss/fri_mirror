package com.project.fri.gameChatting.repository;

import com.project.fri.gameChatting.dto.FindGameChattingMessageResponse;
import java.time.LocalDateTime;
import java.util.List;

public interface GameChattingRepositoryCustom {
  List<FindGameChattingMessageResponse> findGameChattingMessageAndUser(Long roomId, LocalDateTime createdAt);
}
