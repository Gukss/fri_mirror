package com.project.fri.likes.dto;

import lombok.Data;

@Data
public class updateLikesRequest {
    private Long boardId;
    private boolean delete;
}
