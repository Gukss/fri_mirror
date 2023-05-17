package com.project.fri.board.repository;

import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * packageName    : com.project.fri.board.repository fileName       : BoardImageRepository date
 * : 2023-05-10 description    :
 */
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {
    Optional<BoardImage> findTopByBoardAndIsDeleteFalseOrderByCreatedAtAsc(Board board);
    @Query("SELECT b.boardUrl FROM BoardImage b WHERE b.board = :board AND b.isDelete = false")
    List<String> findImageUrlByBoard(@Param("board") Board board);

}
