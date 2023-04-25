package com.project.fri.room.dto;

import com.project.fri.room.entity.Room;
import com.project.fri.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.project.fri.room.dto fileName       : CreateRoomResponse author         :
 * hagnoykmik date           : 2023-04-19 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-04-19        hagnoykmik       최초 생성
 */
@Builder
@AllArgsConstructor
@Data
public class CreateRoomResponse {
  private Long id;
  private String title;
  private boolean ready;

  public static CreateRoomResponse create(Room room, User user) {
    CreateRoomResponse createRoomResponse = CreateRoomResponse.builder()
        .id(room.getId())
        .title(room.getTitle())
        .ready(user.isReady())
        .build();
    return createRoomResponse;
  }
}