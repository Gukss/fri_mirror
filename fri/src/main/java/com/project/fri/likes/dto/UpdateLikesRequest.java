package com.project.fri.likes.dto;

import lombok.Data;

@Data
public class UpdateLikesRequest {
    private Long boardId;
    private boolean delete;
}
