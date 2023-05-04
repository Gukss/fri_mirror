package com.project.fri.gameRoom.dto;

import lombok.Data;

/**
 * packageName    : com.project.fri.gameRoom.dto fileName       :
 * SocketGameRoomStatusUserListInstance date           : 2023-05-03 description    :
 */
@Data
public class SocketGameRoomStatusUserListInstance {
  private Long userId;
  private String userProfile;
  private String nickname;
  private boolean ready;
  private double result;
}
