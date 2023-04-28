package com.project.fri.chatting.dto;

import lombok.Data;

@Data
public class CreateSocketChattingMessageRequest {
  private Long roomId;
  private String message;
  private Long memberId;
}
