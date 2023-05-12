package com.project.fri.comment.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindCommentResponse {
    @NotNull
    private Long boardId;
    @NotNull
    private String title;
    @NotNull
    private String content;
    private int likesCount;
    private int commentCount;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private String boardUrl;
}
