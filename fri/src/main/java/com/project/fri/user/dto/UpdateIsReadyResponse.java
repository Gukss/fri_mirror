package com.project.fri.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * packageName    : com.project.fri.user.dto fileName       : UpdateIsReadyResponse date           :
 * 2023-05-11 description    :
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@AllArgsConstructor
@Builder
public class UpdateIsReadyResponse {
  private boolean ready;

  public static UpdateIsReadyResponse create(boolean ready){
    return UpdateIsReadyResponse.builder()
        .ready(ready)
        .build();
  }
}
