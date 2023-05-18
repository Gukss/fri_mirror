package com.project.fri.board.service;

import com.project.fri.board.dto.*;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.fri.board.dto.CreateBoardRequest;
import com.project.fri.board.dto.DeleteBoardRequest;
import com.project.fri.board.dto.DeleteBoardResponse;
import com.project.fri.board.dto.FindBoardResponse;
import com.project.fri.board.entity.Board;
import com.project.fri.board.entity.BoardCategory;
import com.project.fri.board.entity.BoardImage;
import com.project.fri.board.entity.Category;
import com.project.fri.board.repository.BoardCategoryRepository;
import com.project.fri.board.repository.BoardImageRepository;
import com.project.fri.board.repository.BoardRepository;
import com.project.fri.comment.repository.CommentRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.likes.entity.Likes;
import com.project.fri.likes.repository.LikesRepository;
import com.project.fri.scrap.entity.Scrap;
import com.project.fri.scrap.repository.ScrapRepository;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;
  private final UserRepository userRepository;
  private final BoardCategoryRepository boardCategoryRepository;
  private final BoardImageRepository boardImageRepository;
  private final LikesRepository likesRepository;
  private final CommentRepository commentRepository;
  private final ScrapRepository scrapRepository;

  private final AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  /**
   * desc: 방 생성
   *
   * @param createBoardRequest
   * @param userId
   * @return
   */
  @Override
  @Transactional
  public ResponseEntity<CreateBoardResponse> createBoard(CreateBoardRequest createBoardRequest, List<MultipartFile> boardImage, Long userId) {
    User findUser = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundExceptionMessage(
            NotFoundExceptionMessage.NOT_FOUND_USER));

    log.info("@@@@@@@@@@@@" + createBoardRequest.getBoardCategory());
    //todo: 예외 만들고 변경해주기
    BoardCategory boardCategory = boardCategoryRepository.findByCategory(
        createBoardRequest.getBoardCategory()).orElseThrow(
        () -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM_CATEGORY));
    //board 하나 만들고
    Board board = Board.create(createBoardRequest, findUser, boardCategory);
    Board save = boardRepository.save(board);

    if(boardImage!=null) {
      for (MultipartFile file : boardImage) {
        String urlOrError = createBoardImageUrl(file);

        if (urlOrError.equals("Error")) {
          throw new IllegalStateException("create image error");
        }

        BoardImage createBoardImage = BoardImage.create(urlOrError, findUser, board);
        boardImageRepository.save(createBoardImage);

      }
    }

    CreateBoardResponse createBoardResponse = CreateBoardResponse.create(save.getId());
    return ResponseEntity.ok().body(createBoardResponse);
    //todo: 올린 이미지 boardImage에 넣어주기
  }

  @Override
  public String createBoardImageUrl(MultipartFile file) {
      try {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, fileName, file.getInputStream(),metadata);
        return amazonS3Client.getUrl(bucket, fileName).toString();
      } catch (IOException e) {
        e.printStackTrace();
        return "Error";
      }
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

  /**
   * 게시판 상세 조회
   * @param userId
   * @return
   */
  @Override
  public ReadBoardAndCommentListResponse readBoardDetail(Long boardId, Long userId) {
    Board findBoard = boardRepository.findByIdAndIsDeleteFalseWithUser(boardId)
            .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_BOARD));
    User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));
    // 좋아요, 댓글, 스크랩 수
    long likeCount = findBoard.getLikesCount();
    long commentCount = commentRepository.countByBoardAndIsDeleteFalse(findBoard);
    long scrapCount = scrapRepository.countByBoardAndIsDeleteFalse(findBoard);
    // 내가 좋아요 했는지 여부, 내가 스크랩 했는지 여부
    Optional<Likes> findLikes = likesRepository.findByUserAndBoardAndIsDeleteFalse(findUser, findBoard);
    boolean likes = findLikes.isPresent();
    Optional<Scrap> findScrap = scrapRepository.findByUserAndBoardAndIsDeleteFalse(findUser, findBoard);
    boolean scrap = findScrap.isPresent();
    // 해당 게시글이 내가쓴글인지 확인
    String identity;
    if (findBoard.getUser().getId().equals(findUser.getId())) {
      identity = "na";
    } else {
      identity = "nam";
    }
    // 댓글 리스트
    List<CommentListInstance> commentList = commentRepository.findAllByBoardAndIsDeleteFalseOrderByCreatedAtDescWithBoardAndUser(findBoard).stream()
            .map(c -> new CommentListInstance(c, userId))
            .collect(Collectors.toList());
    // 해당 게시글 사진들 조회
    List<String> boardImageUrlList = boardImageRepository.findImageUrlByBoard(findBoard);

    return ReadBoardAndCommentListResponse.create(findBoard, likeCount, likes, commentCount, boardImageUrlList, commentList, scrapCount, scrap, identity);

  }

  /**
   * 게시판 카테고리별 목록 조회
   * @param category 요청 카테고리
   * @param userId 유저
   * @return 카테고리별 목록 응답 FindAllBoardByRoomCategoryResponse
   */
  @Override
  public FindAllBoardByRoomCategoryResponse findAllBoardByRoomCategory(Category category, Long userId, Pageable pageable) {
    // 잘못된 요청
    if (category == null || userId == null) {
      return null;
    }

    List<Board> choiceBoardList;

    User findUser=userRepository.findById(userId)
            .orElseThrow(()->new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    if (category.toString().equals("HOT")) {
      Pageable newPageable = PageRequest.of(0, pageable.getPageSize(), pageable.getSort());
      // HOT 게시판 조회 2주 동안의 글중 좋아요가 10개 이상인 글
      choiceBoardList = boardRepository.findHotBoardList(LocalDateTime.now().minusWeeks(2), newPageable);

    } else {
      BoardCategory findBoardCategory = boardCategoryRepository.findByCategory(category)
              .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_CATEGORY));
      choiceBoardList = boardRepository.findAllByBoardCategoryAndIsDeleteFalseOrderByCreatedAtDesc(findBoardCategory);

    }

    List<FindBoardByRoomCategoryResponse> boardListByCategory = choiceBoardList.stream()
            .map(b -> {
              Optional<Likes> findLikes = likesRepository.findByUserAndBoardAndIsDeleteFalse(findUser, b);
              long commentCount = commentRepository.countByBoardAndIsDeleteFalse(b);
              Optional<BoardImage> findBoardImage = boardImageRepository.findTopByBoardAndIsDeleteFalseOrderByCreatedAtAsc(b);

              if (findBoardImage.isPresent()) {
                return FindBoardByRoomCategoryResponse.create(b, findLikes.isPresent(), commentCount, findBoardImage.get().getBoardUrl());
              } else {
                return FindBoardByRoomCategoryResponse.create(b, findLikes.isPresent(), commentCount, "");
              }
            }).collect(Collectors.toList());

    return new FindAllBoardByRoomCategoryResponse(boardListByCategory);
  }
}
