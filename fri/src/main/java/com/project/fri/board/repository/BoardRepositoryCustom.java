package com.project.fri.board.repository;

import com.project.fri.board.dto.ReadBoardAndCommentListResponse;
import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface BoardRepositoryCustom{
  ReadBoardAndCommentListResponse readBoardAndCommentList(long boardId);
}
