package com.project.fri.board.repository;

import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * packageName    : com.project.fri.board.repository fileName       : BoardRepository date
 * : 2023-05-11 description    :
 */
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
  Optional<Board> findTop1ByBoardCategoryOrderByCreatedAtDesc(BoardCategory boardCategory);
  @Query("SELECT b FROM Board b JOIN FETCH b.user WHERE b.id = :boardId AND b.isDelete = false")
  Optional<Board> findByIdAndIsDeleteFalseWithUser(@Param("boardId") Long boardId);
  Optional<Board> findByIdAndIsDeleteFalse(@Param("boardId") Long boardId);
  Optional<Board> findByIdAndUserIdAndIsDeleteFalse(Long boardId, Long userId);
}
