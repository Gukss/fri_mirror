package com.project.fri.comment.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCommentRequest {
  @NotNull
  private Long commentId;
  @NotNull
  private String content;
}
