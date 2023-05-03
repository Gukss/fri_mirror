package com.project.fri.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.user.dto fileName       : CertifiedCodeResponse date           :
 * 2023-05-03 description    :
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@AllArgsConstructor
@Builder
public class CertifiedCodeResponse {
  private boolean certifiedCode;
}
