package com.project.fri.scrap.service;

import com.project.fri.board.entity.Board;
import com.project.fri.board.repository.BoardImageRepository;
import com.project.fri.board.repository.BoardRepository;
import com.project.fri.comment.repository.CommentRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.likes.repository.LikesRepository;
import com.project.fri.scrap.dto.CreateScrapRequest;
import com.project.fri.scrap.dto.CreateScrapResponse;
import com.project.fri.scrap.dto.FindScrapListResponse;
import com.project.fri.scrap.dto.FindScrapResponse;
import com.project.fri.scrap.entity.Scrap;
import com.project.fri.scrap.repository.ScrapRepository;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ScrapServiceImpl implements ScrapService {

    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentRepository;
    private final BoardImageRepository boardImageRepository;

    /**
     * scrab 생성
     *
     * @param createScrapRequest 요청
     * @param userId             유저Id
     * @return 스크랩 여부 응답
     */
    @Override
    @Transactional
    public CreateScrapResponse createScrap(CreateScrapRequest createScrapRequest, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

        Board findBoard = boardRepository.findById(createScrapRequest.getBoardId())
                .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_BOARD));

        Optional<Scrap> findScrap = scrapRepository.findByUserAndBoardAndIsDeleteFalse(findUser, findBoard);

        // 해당유저가 스크랩 한 상태이면 잘못된 요청 null 반환
        if (findScrap.isPresent()) {
            return null;
        }

        Scrap scrap = Scrap.create(findUser, findBoard);
        scrapRepository.save(scrap);

        return new CreateScrapResponse(true);
    }

    @Override
    public FindScrapListResponse findScrapList(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

        List<Scrap> scrapList = scrapRepository.findAllByUserAndIsDeleteFalseOrderByCreatedAtAscWithBoard(findUser);

//        scrapList.stream().map(
//                s -> new FindScrapResponse(s.getBoard(),
//                        likesRepository.countByBoardAndIsDeleteFalse(s.getBoard()),
//                        commentRepository.countByBoardAndIsDeleteFalse(s.getBoard()),
//                        )
//        )
        return null;
    }

}
