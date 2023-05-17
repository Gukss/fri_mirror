package com.project.fri.board.controller;

import com.project.fri.board.dto.*;
import com.project.fri.board.entity.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import com.project.fri.board.service.BoardService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

  private final BoardService boardService;

  @PostMapping
  public ResponseEntity createBoard(
          @RequestPart @Validated CreateBoardRequest createBoardRequest,
          @RequestPart(required = false) @Validated List<MultipartFile> boardImage,
          @RequestHeader("Authorization") Long userId) {
    boardService.createBoard(createBoardRequest, boardImage, userId);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<FindBoardResponse>> findBoard() {
    List<FindBoardResponse> board = boardService.findBoard();
    return ResponseEntity.ok().body(board);
  }

  @PatchMapping("/delete")
  public ResponseEntity<DeleteBoardResponse> deleteBoard(
          @RequestBody DeleteBoardRequest deleteBoardRequest) {
    ResponseEntity responseEntity = boardService.deleteBoard(deleteBoardRequest);
    return responseEntity;
  }

  /**
   * 게시판 상세 조회
   *
   * @param boardId 상세 조회하는 게시판 id
   * @param userId  조회하는 유저 id
   * @return 댓글, 좋아요, 이미지 정보가 모두 포함된 게시판 상세 조회 정보
   */
  @GetMapping("/{boardId}")
  public ResponseEntity<ReadBoardAndCommentListResponse> readBoardDetail(
          @PathVariable("boardId") Long boardId, @RequestHeader("Authorization") Long userId) {
    ReadBoardAndCommentListResponse result = boardService.readBoardDetail(boardId, userId);
    return ResponseEntity.ok().body(result);
  }

  /**
   * 게시판 카테고리별 목록 조회
   * @param category 요청 카테고리
   * @param userId 유저
   * @return 카테고리별 목록 응답 FindAllBoardByRoomCategoryResponse
   */
  @GetMapping("/list")
  public ResponseEntity<FindAllBoardByRoomCategoryResponse> findAllBoardByRoomCategory(
          @RequestParam("boardCategory") Category category,
          @RequestHeader("Authorization") Long userId,
          @PageableDefault(size = 20,sort = "likesCount",direction = Sort.Direction.DESC) Pageable pageable
          ) {
    FindAllBoardByRoomCategoryResponse result = boardService.findAllBoardByRoomCategory(category, userId, pageable);

    return ResponseEntity.ok().body(result);
  }
}
