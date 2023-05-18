package com.project.fri.board.service;


import com.project.fri.board.dto.*;

import java.io.IOException;
import java.util.List;

import com.project.fri.board.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {

  /**
   * desc: 방 생성
   * @param createBoardRequest
   * @param userId
   * @return
   */
  ResponseEntity<CreateBoardResponse> createBoard(CreateBoardRequest createBoardRequest, List<MultipartFile> boardImage, Long userId);

  /**
   * desc: 게시판 카테고리 별 단건 조회
   */
  List<FindBoardResponse> findBoard();

  /**
   * desc: 게시판 삭제
   * @param deleteBoardRequest
   * @return
   */
  ResponseEntity deleteBoard(DeleteBoardRequest deleteBoardRequest);

  /**
   * 게시판 상세 조회
   * @param userId
   * @return
   */
  ReadBoardAndCommentListResponse readBoardDetail(Long boardId, Long userId);

  String createBoardImageUrl(MultipartFile boardImage);

  /**
   * 게시판 카테고리별 목록 조회
   * @param category 요청 카테고리
   * @param userId 유저
   * @return 카테고리별 목록 응답 FindAllBoardByRoomCategoryResponse
   */
  FindAllBoardByRoomCategoryResponse findAllBoardByRoomCategory(Category category, Long userId, Pageable pageable);
}
