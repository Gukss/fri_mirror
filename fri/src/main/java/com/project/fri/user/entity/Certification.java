package com.project.fri.user.entity;

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
public class Certification {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="certification_id")
  private Long id;

  @NotNull
  private String email;

  @NotNull
  private String code;

  private boolean isConfirmedEdu;

  private boolean isConfirmedKey;

  public static Certification init(String email, String code, boolean isConfirmedEdu, boolean isConfirmedKey){
    return Certification.builder()
        .email(email)
        .code(code)
        .isConfirmedEdu(isConfirmedEdu) //edu 인증을 마친 것만 db에 들어간다.
        .isConfirmedKey(isConfirmedKey)
        .build();
  }
}
