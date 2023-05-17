package com.project.fri.user.entity;

import com.project.fri.util.BaseTimeEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.user.entity fileName       : certification date           :
 * 2023-05-02 description    :
 */
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="certification")
public class Certification extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="certification_id")
  private Long id;

  @NotNull
  private String email;

  @NotNull
  private String code;

  private boolean isConfirmedEdu;

  private boolean isConfirmedCode;

  private boolean isEmailAgreement;
  @NotNull
  private LocalDateTime emailAgreementAt;

  public static Certification init(String email, String code, boolean isConfirmedEdu, boolean isConfirmedCode, boolean isEmailAgreement){
    return Certification.builder()
        .email(email)
        .code(code)
        .isConfirmedEdu(isConfirmedEdu) //edu 인증을 마친 것만 db에 들어간다.
        .isConfirmedCode(isConfirmedCode)
        .isEmailAgreement(isEmailAgreement)
        .emailAgreementAt(LocalDateTime.now())
        .build();
  }

  public Certification update(String code, boolean isConfirmedEdu, boolean isConfirmedCode, boolean isEmailAgreement, LocalDateTime time){
    this.code = code;
    this.isConfirmedEdu = isConfirmedEdu;
    this.isConfirmedCode = isConfirmedCode;
    this.isEmailAgreement = isEmailAgreement;
    this.update(time);
    return this;
  }

  public Certification updateEmailAgreementAndEmailAgreementAt(boolean isEmailAgreement){
    this.isEmailAgreement = isEmailAgreement;
    this.emailAgreementAt = LocalDateTime.now();
    return this;
  }
}
