package com.project.fri.comment.dto;

import com.project.fri.comment.entity.Comment;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCommentResponse {
  @NotNull
  private Long commentId;
  @NotNull
  private String content;
  @NotNull
  private LocalDateTime createdAt;

  public static CreateCommentResponse create(Comment comment){
    CreateCommentResponse response=CreateCommentResponse.builder()
        .commentId(comment.getId())
        .content(comment.getContent())
        .createdAt(comment.getCreatedAt())
        .build();

    return response;
  }
}
