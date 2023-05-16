package com.project.fri.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.project.fri.common.dto fileName       : ResponseAuth author         : SSAFY
 * date           : 2023-05-16 description    : ===========================================================
 * DATE              AUTHOR             NOTE -----------------------------------------------------------
 * 2023-05-16        SSAFY       최초 생성
 */
@Builder
@Data
@AllArgsConstructor
public class ResponseAuth {

  private String accessToken;
  private String refreshToken;

}
