package com.project.fri.board.service;

import com.project.fri.board.dto.CreateBoardRequest;
import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardCategory;
import com.project.fri.board.repository.BoardCategoryRepository;
import com.project.fri.board.repository.BoardImageRepository;
import com.project.fri.board.repository.BoardRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
  private final BoardRepository boardRepository;
  private final UserRepository userRepository;
  private final BoardCategoryRepository boardCategoryRepository;
  private final BoardImageRepository boardImageRepository;
  @Override
  public void createBoard(CreateBoardRequest createBoardRequest, Long userId) {
    User findUser = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(
            NotFoundExceptionMessage.NOT_FOUND_USER));

    //todo: 예외 만들고 변경해주기
    BoardCategory boardCategory = boardCategoryRepository.findByCategory(
        createBoardRequest.getBoardCategory()).orElseThrow(
        () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM_CATEGORY));
    //board 하나 만들고
    Board board = Board.create(createBoardRequest, findUser, boardCategory);
    boardRepository.save(board);

    //todo: 올린 이미지 boardImage에 넣어주기
  }
}
