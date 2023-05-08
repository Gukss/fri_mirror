package com.project.fri.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.user.dto fileName       : CertifiedNicknameRequest date
 * : 2023-05-08 description    :
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@AllArgsConstructor
@Builder
public class CertifiedNicknameRequest {
  private String nickname;
}
