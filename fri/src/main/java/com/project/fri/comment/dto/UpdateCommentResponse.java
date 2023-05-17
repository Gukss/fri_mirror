package com.project.fri.comment.dto;

import com.project.fri.comment.entity.Comment;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCommentResponse {
  @NotNull
  private Long commentId;
  @NotNull
  private String content;

  public static UpdateCommentResponse create(Comment comment){
    UpdateCommentResponse response=UpdateCommentResponse.builder().commentId(comment.getId())
        .content(comment.getContent()).build();
    return response;
  }
}
