package com.project.fri.likes.dto;

import lombok.Data;

import javax.validation.constraints.AssertFalse;

@Data
public class DeleteLikesRequest {
    private Long boardId;
    @AssertFalse
    private boolean delete;
}
