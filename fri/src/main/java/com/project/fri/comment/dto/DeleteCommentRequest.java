package com.project.fri.comment.dto;

import lombok.Data;

@Data
public class DeleteCommentRequest {
  private Long commentId;
  private boolean isDelete;
}
