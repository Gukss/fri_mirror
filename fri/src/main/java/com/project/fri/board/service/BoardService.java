package com.project.fri.board.service;


import com.project.fri.board.dto.CreateBoardRequest;
import com.project.fri.board.dto.DeleteBoardRequest;
import com.project.fri.board.dto.FindBoardResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface BoardService {

  /**
   * desc: 방 생성
   * @param createBoardRequest
   * @param userId
   * @return
   */
  void createBoard(CreateBoardRequest createBoardRequest, Long userId);

  /**
   * desc: 게시판 카테고리 별 단건 조회
   */
  List<FindBoardResponse> findBoard();

  ResponseEntity deleteBoard(DeleteBoardRequest deleteBoardRequest);
}
