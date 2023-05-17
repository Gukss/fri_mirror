package com.project.fri.user.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * packageName    : com.project.fri.user.dto fileName       : UpdateIsReadyRequest date           :
 * 2023-05-11 description    :
 */
@Data
public class UpdateIsReadyRequest {
  @NotNull
  private Long gameRoomId;

  private boolean ready;
}
