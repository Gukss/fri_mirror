package com.project.fri.board.repository;

import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardCategory;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * packageName    : com.project.fri.board.repository fileName       : BoardRepository date
 * : 2023-05-11 description    :
 */
public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
  Optional<Board> findTop1ByBoardCategoryOrderByCreatedAtDesc(BoardCategory boardCategory);
  Optional<Board> findByIdAndIsDeleteFalse(Long boardId);
  @Query("SELECT b FROM Board b JOIN FETCH b.user WHERE b.id = :boardId AND b.isDelete = false")
  Optional<Board> findByIdAndIsDeleteFalseWithUser(@Param("boardId") Long boardId);
  @Query("SELECT b FROM Board b WHERE b.createdAt >= :twoWeeksAgo AND b.likesCount >= 10 AND b.isDelete = false")
  List<Board> findHotBoardList(@Param("twoWeeksAgo") LocalDateTime twoWeeksAgo, Pageable pageable);
  List<Board> findAllByBoardCategoryAndIsDeleteFalseOrderByCreatedAtDesc(BoardCategory boardCategory);
}
