package com.project.fri.room.dto;

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

  private String title;
  private boolean ready;
}
