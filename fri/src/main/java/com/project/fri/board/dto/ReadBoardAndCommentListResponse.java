package com.project.fri.board.dto;

import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardImage;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.board.dto fileName       : ReadBoardAndCommentListResponse date
 * : 2023-05-11 description    :
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@AllArgsConstructor
@Builder
public class ReadBoardAndCommentListResponse {
  private long boardId;
  @NotNull
  private String title;
  @NotNull
  private String content;
  private long likesCount;
  private boolean likes;
  private long commentCount;
  private LocalDateTime createAt;
  private String boardUrl;

  @QueryProjection
  public ReadBoardAndCommentListResponse(Board board, long likesCount, boolean likes, long commentCount, LocalDateTime createAt, BoardImage boardImage){

  }
}
