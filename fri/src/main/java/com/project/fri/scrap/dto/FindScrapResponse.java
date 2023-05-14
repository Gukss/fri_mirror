package com.project.fri.scrap.dto;

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

}
