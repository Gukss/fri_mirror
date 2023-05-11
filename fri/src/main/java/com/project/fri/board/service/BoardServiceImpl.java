package com.project.fri.board.service;

import com.project.fri.board.dto.CreateBoardRequest;
import com.project.fri.board.dto.DeleteBoardRequest;
import com.project.fri.board.dto.DeleteBoardResponse;
import com.project.fri.board.dto.FindBoardResponse;
import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardCategory;
import com.project.fri.board.entity.Category;
import com.project.fri.board.repository.BoardCategoryRepository;
import com.project.fri.board.repository.BoardImageRepository;
import com.project.fri.board.repository.BoardRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

  /**
   * desc: 방 생성
   *
   * @param createBoardRequest
   * @param userId
   * @return
   */
  @Override
  @Transactional
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

  /**
   * desc: 게시판 카테고리 별 단건 조회
   */
  @Override
  public List<FindBoardResponse> findBoard() {
    ArrayList<FindBoardResponse> list = new ArrayList<>();
    for (Category x : Category.values()) {
      //todo: 예외 변경하기
      BoardCategory boardCategory = boardCategoryRepository.findByCategory(x).orElseThrow(
          () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM_CATEGORY));
      Optional<Board> optionalBoard = boardRepository.findTop1ByBoardCategoryOrderByCreatedAtDesc(
          boardCategory);
      if (!optionalBoard.isPresent()) { //해당 카테고리의 게시글이 없다면
        continue;
      }
      //todo: 예외 만들기
      Board board = optionalBoard.orElseThrow(
          () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM));
      FindBoardResponse findBoardResponse = new FindBoardResponse(board.getId(), board.getTitle(),
          board.getBoardCategory().getCategory());
      list.add(findBoardResponse.create());
    }
    return list;
  }

  @Override
  @Transactional
  public ResponseEntity deleteBoard(DeleteBoardRequest deleteBoardRequest) {
    ResponseEntity res = null;
    if(!deleteBoardRequest.isDelete()){
      res = ResponseEntity.badRequest().build();
      return res;
    }
    //todo: 예외 변경하기
    Board board = boardRepository.findById(deleteBoardRequest.getBoardId())
        .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
    //todo: delete가 false로 들어오면 400 던져야한다.
    Board updateIsDeleteBoard = board.updateIsDelete(deleteBoardRequest.isDelete());
    DeleteBoardResponse instance = DeleteBoardResponse.create(updateIsDeleteBoard.getId());
    res = ResponseEntity.ok().body(instance);
    return res;
  }

  @Override
  public ResponseEntity readBoardAndCommentList(long userId) {

    return null;
  }
}
