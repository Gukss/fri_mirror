package com.project.fri.comment.controller;

import com.project.fri.comment.dto.CreateCommentRequest;
import com.project.fri.comment.dto.CreateCommentResponse;
import com.project.fri.comment.dto.DeleteCommentRequest;
import com.project.fri.comment.dto.DeleteCommentResponse;
import com.project.fri.comment.dto.UpdateCommentRequest;
import com.project.fri.comment.dto.UpdateCommentResponse;
import com.project.fri.comment.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
@Slf4j
public class CommentController {
  private final CommentServiceImpl commentService;

  /**
   * 댓글 생성
   * @param request
   * @param userId
   * @return
   */
  @PostMapping
  public ResponseEntity<CreateCommentResponse> createComment(@RequestBody CreateCommentRequest request,
      @RequestHeader("Authorization") Long userId){
    CreateCommentResponse response = commentService.CreateComment(request, userId);
    return ResponseEntity.status(201).body(response);
  }

  /**
   * 댓글 삭제
   * @param request
   * @param userId
   * @return
   */
  @PatchMapping("/delete")
  public ResponseEntity<DeleteCommentResponse> deleteComment(@RequestBody DeleteCommentRequest request,@RequestHeader("Authorization")Long userId){
    DeleteCommentResponse response = commentService.DeleteComment(request, userId);
    return ResponseEntity.status(201).body(response);
  }

  /**
   * 댓글 수정
   * @param request
   * @param userId
   * @return
   */
  @PatchMapping
  public ResponseEntity<UpdateCommentResponse> updateComment(@RequestBody UpdateCommentRequest request,@RequestHeader("Authorization")Long userId){
    UpdateCommentResponse response=commentService.UpdateComment(request,userId);
    return ResponseEntity.status(201).body(response);
  }

//  @GetMapping
//  public ResponseEntity<?> getComment(@RequestHeader("Authorization")Long userId){
//
//  }

}
