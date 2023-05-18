package com.project.fri.board.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.board.dto fileName       : CreateBoardResponse date           :
 * 2023-05-18 description    :
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@AllArgsConstructor
@Builder
public class CreateBoardResponse {
  private long boardId;

  public static CreateBoardResponse create(long boardId){
    return CreateBoardResponse.builder()
        .boardId(boardId)
        .build();
  }

}
