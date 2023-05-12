package com.project.fri.comment.service;

import com.project.fri.comment.dto.CreateCommentRequest;
import com.project.fri.comment.dto.CreateCommentResponse;
import com.project.fri.comment.dto.DeleteCommentRequest;
import com.project.fri.comment.dto.DeleteCommentResponse;
import com.project.fri.comment.dto.FindCommentResponse;
import com.project.fri.comment.dto.UpdateCommentRequest;
import com.project.fri.comment.dto.UpdateCommentResponse;

public interface CommentService {
  CreateCommentResponse CreateComment(CreateCommentRequest request,Long userId);
  DeleteCommentResponse DeleteComment(DeleteCommentRequest request,Long userId);
  UpdateCommentResponse UpdateComment(UpdateCommentRequest request,Long userId);
  FindCommentResponse FindComment(Long userId);
}
