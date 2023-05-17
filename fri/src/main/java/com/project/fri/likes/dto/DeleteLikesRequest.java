package com.project.fri.likes.dto;

import lombok.Data;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

@Data
public class DeleteLikesRequest {
    private Long boardId;
    @AssertTrue
    private boolean delete;
}
