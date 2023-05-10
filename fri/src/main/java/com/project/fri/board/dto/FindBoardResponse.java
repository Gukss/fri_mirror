package com.project.fri.board.dto;

import com.project.fri.board.entity.Category;
import com.project.fri.room.dto.FindRoomResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.project.fri.board.dto fileName       : FindBoardResponse date           :
 * 2023-05-10 description    :
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@AllArgsConstructor
@Builder
public class FindBoardResponse {
  private Long boardId;
  private String title;
  private Category category;
  public FindBoardResponse create(){
    return FindBoardResponse.builder()
        .boardId(this.boardId)
        .title(this.title)
        .category(this.category)
        .build();
  }
}
