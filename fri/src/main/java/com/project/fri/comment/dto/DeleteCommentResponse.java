package com.project.fri.comment.dto;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteCommentResponse {
  @NotNull
  private Long commentId;

  public static DeleteCommentResponse create(Long commentId){
      DeleteCommentResponse response= DeleteCommentResponse.builder().commentId(commentId).build();
      return response;
  }
}
