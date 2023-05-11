package com.project.fri.board.repository;

import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.project.fri.board.repository fileName       : BoardRepository date
 * : 2023-05-11 description    :
 */
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
  Optional<Board> findTop1ByBoardCategoryOrderByCreatedAtDesc(BoardCategory boardCategory);
}
