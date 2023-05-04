package com.project.fri.gameChatting.dto;

import lombok.Data;

@Data
public class SocketGameChattingRequestAndResponse {
  private Long gameRoomId;
  private String message;
  private Long memberId;
  private String profileUrl;
  private String nickname;
}
