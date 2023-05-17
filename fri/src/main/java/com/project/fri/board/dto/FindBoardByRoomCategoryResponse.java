package com.project.fri.board.dto;

import com.project.fri.board.entity.Board;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FindBoardByRoomCategoryResponse {
    private Long boardId;
    private String nickname;
    private String title;
    private String content;
    private long likeCount;
    private long commentCount;
    private boolean like;
    private LocalDateTime createdAt;
    private String boardThumbnailUrl;

    public static FindBoardByRoomCategoryResponse create(Board board, boolean like, long commentCount,
                                                         String boardThumbnailUrl) {
        return FindBoardByRoomCategoryResponse.builder()
                .boardId(board.getId())
                .nickname(board.getUser().getNickname())
                .title(board.getTitle())
                .content(board.getContent())
                .likeCount(board.getLikesCount())
                .commentCount(commentCount)
                .like(like)
                .createdAt(board.getCreatedAt())
                .boardThumbnailUrl(boardThumbnailUrl)
                .build();
    }
}
