package com.project.fri.scrap.service;

import com.project.fri.board.entity.Board;
import com.project.fri.board.repository.BoardRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.scrap.dto.CreateScrabRequest;
import com.project.fri.scrap.dto.CreateScrabResponse;
import com.project.fri.scrap.entity.Scrap;
import com.project.fri.scrap.repository.ScrapRepository;
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
public class ScrapServiceImpl implements ScrapService {

    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    /**
     * scrab 생성
     * @param createScrabRequest 요청
     * @param userId 유저Id
     * @return 스크랩 여부 응답
     */
    @Override
    @Transactional
    public CreateScrabResponse createScrab(CreateScrabRequest createScrabRequest, Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

        Board findBoard = boardRepository.findById(createScrabRequest.getBoardId())
                .orElseThrow(() -> new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_BOARD));

        Optional<Scrap> findScrap = scrapRepository.findByUserAndBoardAndIsDeleteFalse(findUser, findBoard);

        // 해당유저가 스크랩 한 상태이면 잘못된 요청 null 반환
        if (findScrap.isPresent()) {
            return null;
        }

        Scrap scrap = Scrap.create(findUser, findBoard);
        scrapRepository.save(scrap);

        return new CreateScrabResponse(true);
    }

}
