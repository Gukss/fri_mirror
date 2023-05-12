package com.project.fri.comment.service;

import com.project.fri.board.entity.Board;
import com.project.fri.board.repository.BoardRepository;
import com.project.fri.comment.dto.CreateCommentRequest;
import com.project.fri.comment.dto.CreateCommentResponse;
import com.project.fri.comment.dto.DeleteCommentRequest;
import com.project.fri.comment.dto.DeleteCommentResponse;
import com.project.fri.comment.dto.FindCommentResponse;
import com.project.fri.comment.dto.UpdateCommentRequest;
import com.project.fri.comment.dto.UpdateCommentResponse;
import com.project.fri.comment.entity.Comment;
import com.project.fri.comment.repository.CommentRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import com.sun.jdi.request.InvalidRequestStateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{
  private final CommentRepository commentRepository;
  private final BoardRepository boardRepository;
  private final UserRepository userRepository;
  @Override
  @Transactional
  public CreateCommentResponse CreateComment(CreateCommentRequest request, Long userId) {
    User user=userRepository.findById(userId)
            .orElseThrow(()->new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    Board board=boardRepository.findById(request.getBoardId())
            .orElseThrow(()->new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_BOARD));

    Comment comment = Comment.create(request, user, board);

    commentRepository.save(comment);

    CreateCommentResponse createCommentResponse=CreateCommentResponse.create(comment);
    return createCommentResponse;
  }

  @Override
  @Transactional
  public DeleteCommentResponse DeleteComment(DeleteCommentRequest request, Long userId) {
    User user=userRepository.findById(userId)
        .orElseThrow(()->new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    Comment comment=commentRepository.findById(request.getCommentId())
        .orElseThrow(()->new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_COMMENT));

    // 현재 댓글을 지우는 사람이 댓글 작성자가 아닐 경우 삭제
    // todo : 관리자 존재가 생기면 로직 변경 필요
    if(user.getId()!=comment.getUser().getId()){
      throw new InvalidRequestStateException();
    }

    comment.updateIsDelete(true);
    DeleteCommentResponse response= DeleteCommentResponse.create(comment.getId());
    return response;
  }

  @Override
  @Transactional
  public UpdateCommentResponse UpdateComment(UpdateCommentRequest request, Long userId) {
    User user=userRepository.findById(userId)
        .orElseThrow(()->new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    Comment comment=commentRepository.findById(request.getCommentId())
        .orElseThrow(()->new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_COMMENT));

    // 현재 댓글을 지우는 사람이 댓글 작성자가 아닐 경우 삭제
    // todo : 관리자 존재가 생기면 로직 변경 필요
    if(user.getId()!=comment.getUser().getId()){
      throw new InvalidRequestStateException();
    }

    // 영속성 컨텍스트에서 값을 업데이트해서 comment를 바로 response로 전달해도 변경사항 감지
    comment.updateComment(request.getContent());

    UpdateCommentResponse response= UpdateCommentResponse.create(comment);
    return response;
  }

  @Override
  public FindCommentResponse FindComment(Long userId) {
    User user=userRepository.findById(userId)
        .orElseThrow(()->new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));


    return null;
  }

}
