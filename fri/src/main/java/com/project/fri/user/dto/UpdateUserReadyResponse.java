package com.project.fri.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.user.dto fileName       : UpdateUserReadyResponse date
 * : 2023-04-21 description    :
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserReadyResponse {
  long roomId;

  boolean ready;

  boolean isConfirmed;
}
