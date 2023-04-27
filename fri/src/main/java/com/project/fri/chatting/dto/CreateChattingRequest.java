package com.project.fri.chatting.dto;

import lombok.Data;

@Data
public class CreateChattingRequest {
  private long roomId;
  private String message;
}
