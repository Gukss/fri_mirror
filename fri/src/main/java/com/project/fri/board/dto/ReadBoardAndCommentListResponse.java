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
  private String nickname;
  private String anonymousProfileImageUrl;
  @NotNull
  private String title;
  @NotNull
  private String content;
  private long likesCount;
  private long commentCount;
  private long scrapCount;
  private boolean likes;
  private boolean scrap;
  private LocalDateTime createAt;
  private List<String> boardImage;

  private List<CommentListInstance> commentList;

  /*
  todo: queryDsl
  @QueryProjection
  public ReadBoardAndCommentListResponse(Board board, long likesCount, boolean likes, long commentCount, LocalDateTime createAt, BoardImage boardImage){
  }
  */

  public static ReadBoardAndCommentListResponse create(Board board, long likesCount, boolean likes, long commentCount,
                                                       List<String> boardImage, List<CommentListInstance> commentList,
                                                       long scrapCount, boolean scrap) {
    return ReadBoardAndCommentListResponse.builder()
            .boardId(board.getId())
            .nickname(board.getUser().getNickname())
            .anonymousProfileImageUrl(board.getUser().getAnonymousProfileImage().getImageUrl())
            .title(board.getTitle())
            .content(board.getContent())
            .likesCount(likesCount)
            .likes(likes)
            .commentCount(commentCount)
            .scrapCount(scrapCount)
            .scrap(scrap)
            .createAt(board.getCreatedAt())
            .boardImage(boardImage)
            .commentList(commentList)
            .build();
  }
}
