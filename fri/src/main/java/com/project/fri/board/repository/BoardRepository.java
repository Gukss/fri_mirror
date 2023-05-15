package com.project.fri.board.repository;

import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
  Optional<Board> findTop1ByBoardCategoryOrderByCreatedAtDesc(BoardCategory boardCategory);
  Optional<Board> findByIdAndIsDeleteFalse(Long boardId);
}
