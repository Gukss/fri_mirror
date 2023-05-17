package com.project.fri.scrap.controller;

import com.project.fri.scrap.dto.*;
import com.project.fri.scrap.service.ScrapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scrap")
@Slf4j
public class ScrapController {

    private final ScrapService scrapService;

    /**
     * 스크랩 생성
     * @param createScrapRequest boordId
     * @param userId header
     * @return 스크랩 상태 true 반환
     */
    @PostMapping
    public ResponseEntity<CreateScrapResponse> createScrap(
            @RequestBody CreateScrapRequest createScrapRequest,
            @RequestHeader("Authorization") Long userId) {
        CreateScrapResponse result = scrapService.createScrap(createScrapRequest, userId);
        // null 리턴 받으면 이미 스크랩한 상태 잘못된요청에 현재 스크랩 상태를 알려주기위헤 ture 반환
        if (result == null) {
            return ResponseEntity.badRequest().body(new CreateScrapResponse(true));
        }

        return ResponseEntity.ok().body(result);
    }

    /**
     * scrap 목록 조회
     * @param userId header
     * @return 스크랩 목록 반환
     */
    @GetMapping
    public ResponseEntity<FindScrapListResponse> findScrapList(@RequestHeader("Authorization") Long userId) {
        FindScrapListResponse result = scrapService.findScrapList(userId);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping
    public ResponseEntity<DeleteScrapResponse> deleteScrap(
            @RequestBody DeleteScrapRequest deleteScrapRequest,
            @RequestHeader("Authorization") Long userId) {
        DeleteScrapResponse result = scrapService.deleteScrap(deleteScrapRequest, userId);

        if (result == null) {
            return ResponseEntity.badRequest().body(new DeleteScrapResponse(true));
        }
        return ResponseEntity.ok().body(result);
    }
}
