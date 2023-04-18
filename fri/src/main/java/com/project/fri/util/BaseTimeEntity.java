package com.project.fri.util;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * packageName    : com.project.fri.util fileName       : BaseTimeEntity date           : 2023-04-18
 * description    :
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

  @CreatedDate
  @NotNull
  private LocalDateTime createdAt; //생성일자

  @LastModifiedDate
  @NotNull // notBlank 사용시 에러남.
  private LocalDateTime updatedAt; //수정일자
}