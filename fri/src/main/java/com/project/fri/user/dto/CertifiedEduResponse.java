package com.project.fri.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.user.dto fileName       : CreateUserResponse date           :
 * 2023-04-30 description    :
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@AllArgsConstructor
@Builder
public class CertifiedEduResponse {
  private boolean certifiedEdu;

  private String code;
}
