package com.project.fri.scrap.dto;

import com.project.fri.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FindScrapResponse {
    private Long boardId;
    private String title;
    private String content;
    private int likesCount;
    private int commentCount;
    private LocalDateTime createdAt;
    private String boardImageThumbnail;

    public FindScrapResponse(Board board, int likes, int comments, String boardImageThumbnailUrl) {
        boardId = board.getId();
        title = board.getTitle();
        content = board.getContent();
        createdAt = board.getCreatedAt();
        likesCount = likes;
        commentCount = comments;
        boardImageThumbnail = boardImageThumbnailUrl;
    }
}
