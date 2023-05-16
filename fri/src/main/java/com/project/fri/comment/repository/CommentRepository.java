package com.project.fri.comment.repository;

import com.project.fri.board.entity.Board;
import com.project.fri.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long>,CommentRepositoryCustom {

    long countByBoardAndIsDeleteFalse(Board board);
}
