package com.project.fri.scrap.service;

import com.project.fri.scrap.dto.CreateScrabRequest;
import com.project.fri.scrap.dto.CreateScrabResponse;

public interface ScrapService {

    /**
     * scrab 생성
     * @param createScrabRequest 요청
     * @param userId 유저Id
     * @return 스크랩 여부 응답
     */
    CreateScrabResponse createScrab(CreateScrabRequest createScrabRequest, Long userId);

}
