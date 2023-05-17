package com.project.fri.user.dto;

import lombok.Builder;
import lombok.Data;

/**
 * packageName    : com.project.fri.user.dto fileName       : UpdateEmailAgreementResponse date
 * : 2023-05-17 description    :
 */
@Data
@Builder
public class UpdateEmailAgreementResponse {
  private boolean emailAgreement;

  public static UpdateEmailAgreementResponse create(boolean emailAgreement){
    return UpdateEmailAgreementResponse.builder()
        .emailAgreement(emailAgreement)
        .build();
  }
}
