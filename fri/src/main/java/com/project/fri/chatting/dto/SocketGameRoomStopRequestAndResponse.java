package com.project.fri.chatting.dto;

import lombok.Data;

/**
 * packageName    : com.project.fri.chatting.dto fileName       :
 * SocketGameRoomStopRequestAndResponse date           : 2023-05-04 description    :
 */
@Data
public class SocketGameRoomStopRequestAndResponse {
  private long gameRoomId;
  private long userId;
  private double gameTime;
  private String anonymousProfileImageId;
  private String nickname;
}
