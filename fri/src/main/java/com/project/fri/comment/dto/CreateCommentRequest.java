package com.project.fri.comment.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentRequest {
  @NotNull
  private String content;
  @NotNull
  private Long boardId;
}
