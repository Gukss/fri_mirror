package com.project.fri.scrap.service;

import com.project.fri.scrap.dto.*;

public interface ScrapService {

    /**
     * scrab 생성
     * @param createScrapRequest 요청
     * @param userId 유저Id
     * @return 스크랩 여부 응답
     */
    CreateScrapResponse createScrap(CreateScrapRequest createScrapRequest, Long userId);

    /**
     * scrap 목록 조회
     * @param userId header
     * @return 스크랩 목록 반환
     */
    FindScrapListResponse findScrapList(Long userId);

    /**
     * scrap 취소
     * @param deleteScrapRequest 요청
     * @param userId header
     * @return 스크랩 취소 결과 응답
     */
    DeleteScrapResponse deleteScrap(DeleteScrapRequest deleteScrapRequest, Long userId);
}
