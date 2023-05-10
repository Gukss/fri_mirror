package com.project.fri.board.service;


import com.project.fri.board.dto.CreateBoardRequest;

public interface BoardService {

  /**
   * desc: 방 생성
   * @param createBoardRequest
   * @param userId
   * @return
   */
  void createBoard(CreateBoardRequest createBoardRequest, Long userId);
}
