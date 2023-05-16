package com.project.fri.scrap.dto;

import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardImage;
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

    public FindScrapResponse(Board board, int likes, int comments, BoardImage boardImage) {
        boardId = board.getId();
        title = board.getTitle();
        content = board.getContent();
        createdAt = board.getCreatedAt();
        likesCount = likes;
        commentCount = comments;
        if (boardImage == null) {
            boardImageThumbnail = "게시물에 사진이 존재하지 않습니다.";
        } else {
            boardImageThumbnail = boardImage.getBoardUrl();
        }
    }
}
