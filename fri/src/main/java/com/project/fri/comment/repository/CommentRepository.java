package com.project.fri.comment.repository;

import com.project.fri.board.entity.Board;
import com.project.fri.comment.entity.Comment;
import com.project.fri.scrap.entity.Scrap;
import com.project.fri.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>,CommentRepositoryCustom {

    long countByBoardAndIsDeleteFalse(Board board);
//    @Query("SELECT c FROM Comment c JOIN FETCH c.board.user.id JOIN FETCH c.user.id WHERE c.board = :board And c.isDelete = false ORDER BY c.createdAt DESC")
    @Query("SELECT c FROM Comment c JOIN FETCH c.board JOIN FETCH c.user WHERE c.board = :board And c.isDelete = false ORDER BY c.createdAt DESC")
    List<Comment> findAllByBoardAndIsDeleteFalseOrderByCreatedAtDescWithBoardAndUser(@Param("board") Board board);
}
