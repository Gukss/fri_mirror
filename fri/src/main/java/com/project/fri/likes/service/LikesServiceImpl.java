package com.project.fri.likes.service;

import com.project.fri.board.entity.Board;
import com.project.fri.board.repository.BoardRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.likes.dto.CreateLikesRequest;
import com.project.fri.likes.dto.CreateLikesResponse;
import com.project.fri.likes.dto.UpdateLikesRequest;
import com.project.fri.likes.dto.UpdateLikesResponse;
import com.project.fri.likes.entity.Likes;
import com.project.fri.likes.repository.LikesRepository;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LikesServiceImpl implements LikesService{

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    /**
     * 좋아요 생성
     * @param createLikesRequest 요청
     * @param userId Authorization
     * @return 응답
     */
    @Override
    @Transactional
    public CreateLikesResponse createLikes(CreateLikesRequest createLikesRequest, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

        Board findBoard = boardRepository.findById(createLikesRequest.getBoardId())
                .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_BOARD));

        Likes findLikesByUserAndBoard = likesRepository.findByUserAndBoard(findUser, findBoard)
                .orElse(null);
        // 해당 유저가 게시글에 좋아요를 한 상태이고, 삭제되지 않은 상태면 잘못된 요청이 들어온것
        if (findLikesByUserAndBoard != null && !findLikesByUserAndBoard.isDelete()) {
            return null;
        }

        Likes likes = Likes.craete(findBoard, findUser);
        likesRepository.save(likes);

        return new CreateLikesResponse(true);
    }

    @Override
    @Transactional
    public UpdateLikesResponse updateLikes(UpdateLikesRequest updateLikesRequest, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

        Board findBoard = boardRepository.findById(updateLikesRequest.getBoardId())
                .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_BOARD));

        Likes findLikesByUserAndBoard = likesRepository.findByUserAndBoard(findUser, findBoard)
                .orElse(null);


    }


}
