package com.project.fri.board.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.board.dto fileName       : CommentListInstance date           :
 * 2023-05-11 description    :
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@AllArgsConstructor
@Builder
public class CommentListInstance {
  @NotNull
  private String content;
  @NotNull
  private LocalDateTime createdAt;
  private long commentId;
}
