package com.project.fri.board.dto;

import com.project.fri.board.entity.Category;
import java.util.ArrayList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * packageName    : com.project.fri.board.dto fileName       : CreateBoardRequest date           :
 * 2023-05-10 description    :
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@AllArgsConstructor
@Builder
public class CreateBoardRequest {
  private Category boardCategory;
  private String title;
  private String content;
}
