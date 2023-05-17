package com.project.fri.gameChatting.dto;

import lombok.Data;

@Data
public class CreateGameChattingMessageRequest {
  private Long gameRoomId;
  private String message;
}
