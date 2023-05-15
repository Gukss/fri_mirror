package com.project.fri.board.repository;

import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * packageName    : com.project.fri.board.repository fileName       : BoardImageRepository date
 * : 2023-05-10 description    :
 */
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    Optional<BoardImage> findTopByBoardAndIsDeleteFalseOrderByCreatedAtDesc(Board board);
}
