package com.project.fri.board.controller;

import com.project.fri.board.dto.CreateBoardRequest;
import com.project.fri.board.dto.DeleteBoardRequest;
import com.project.fri.board.dto.DeleteBoardResponse;
import com.project.fri.board.dto.FindBoardResponse;
import com.project.fri.board.service.BoardService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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
      @RequestPart @Validated ArrayList<MultipartFile> boardImage,
      @RequestHeader("Authorization") Long userId) {
    log.info("img size: "+boardImage.size());
    boardService.createBoard(createBoardRequest, userId);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<FindBoardResponse>> findBoard(){
    List<FindBoardResponse> board = boardService.findBoard();
    return ResponseEntity.ok().body(board);
  }

  @PatchMapping("/delete")
  public ResponseEntity<DeleteBoardResponse> deleteBoard(@RequestBody DeleteBoardRequest deleteBoardRequest){
    ResponseEntity responseEntity = boardService.deleteBoard(deleteBoardRequest);
    return responseEntity;
  }
}
