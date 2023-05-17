package com.project.fri.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FindAllBoardByRoomCategoryResponse {
    private List<FindBoardByRoomCategoryResponse> boardList;
}
