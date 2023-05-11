package com.project.fri.gameRoom.dto;

import java.util.ArrayList;
import lombok.Data;

/**
 * packageName    : com.project.fri.gameRoom.dto fileName       :
 * SocketGameRoomStatusRequestAndResponse date           : 2023-05-03 description    :
 */
@Data
public class SocketGameRoomStatusRequestAndResponse {
  private long gameRoomId;
  private ArrayList<SocketGameRoomStatusUserListInstance> userList;

}
