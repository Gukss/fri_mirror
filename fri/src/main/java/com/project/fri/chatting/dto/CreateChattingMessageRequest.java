package com.project.fri.chatting.dto;

import lombok.Data;

@Data
public class CreateChattingMessageRequest {
  private long roomId;
  private String message;
}
