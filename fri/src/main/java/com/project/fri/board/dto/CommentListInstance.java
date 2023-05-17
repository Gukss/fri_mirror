package com.project.fri.board.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

import com.project.fri.board.entity.Board;
import com.project.fri.comment.entity.Comment;
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
  private Long commentId;
  private String nickname;
  @NotNull
  private String content;
  @NotNull
  private LocalDateTime createdAt;

  private String identity;

  public CommentListInstance(Comment comment, Long userId) {
    commentId = comment.getId();
    nickname = comment.getUser().getNickname();
    content = comment.getContent();
    createdAt = comment.getCreatedAt();
    if (comment.getUser().getId().equals(userId)) {
      identity = "na"; // 내가 작성한 댓글
    } else if (comment.getBoard().getUser().getId().equals(comment.getUser().getId())) {
      identity = "writer"; //댓글이 달린 게시글의 작성자
    } else {
      identity = "nam"; // 남이 작성한 댓글
    }
  }
}
