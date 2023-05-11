package com.project.fri.board.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.board.dto fileName       : DeleteBoardResponse date           :
 * 2023-05-10 description    :
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@AllArgsConstructor
@Builder
public class DeleteBoardResponse {
  private long boardId;
  public static DeleteBoardResponse create(long boardId){
    return DeleteBoardResponse.builder()
        .boardId(boardId)
        .build();
  }
}
