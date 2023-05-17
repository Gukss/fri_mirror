package com.project.fri.scrap.dto;

import lombok.Data;

@Data
public class DeleteScrapRequest {
    private Long boardId;
    private boolean delete;
}
