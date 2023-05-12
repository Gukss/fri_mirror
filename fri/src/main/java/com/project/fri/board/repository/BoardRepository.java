package com.project.fri.board.repository;

import com.project.fri.board.entity.Board;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
  Optional<Board> findById(Long boardId);
}
