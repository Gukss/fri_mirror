package com.project.fri.board.repository;

import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardCategory;
import com.project.fri.board.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.project.fri.board.repository fileName       : BoardCategoryRepository date
 * : 2023-05-10 description    :
 */
public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {
  Optional<BoardCategory> findByCategory(Category category);
}
