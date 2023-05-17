package com.project.fri.board.dto;

import com.project.fri.board.entity.Category;
import java.util.ArrayList;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * packageName    : com.project.fri.board.dto fileName       : CreateBoardRequest date           :
 * 2023-05-10 description    :
 */
@Data
public class CreateBoardRequest {
  private Category boardCategory;
  private String title;
  private String content;
}
