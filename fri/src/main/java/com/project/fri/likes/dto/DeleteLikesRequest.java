package com.project.fri.likes.dto;

import lombok.Data;

@Data
public class DeleteLikesRequest {
    private Long boardId;
    private boolean delete;
}
