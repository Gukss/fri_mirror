package com.project.fri.util;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.util fileName       : BaseEntity date           : 2023-04-18
 * description    :
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
@AllArgsConstructor
@Builder
public class BaseEntity {

  //생성자
//  @NotBlank
  private String constructor;

  //수정자
//  @NotBlank
  private String updater;
}
