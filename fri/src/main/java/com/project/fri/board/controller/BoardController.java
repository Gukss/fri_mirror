package com.project.fri.board.controller;

import com.project.fri.board.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.fri.board.service.BoardService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
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
   * @param boardId 상세 조회하는 게시판 id
   * @param userId 조회하는 유저 id
   * @return 댓글, 좋아요, 이미지 정보가 모두 포함된 게시판 상세 조회 정보
   */
  @GetMapping("/{boardId}")
  public ResponseEntity<ReadBoardAndCommentListResponse> readBoardDetail(
          @PathVariable("boardId") Long boardId, @RequestHeader("Authorization") Long userId) {
    ReadBoardAndCommentListResponse result = boardService.readBoardDetail(boardId, userId);
    return ResponseEntity.ok().body(result);
  }
}
