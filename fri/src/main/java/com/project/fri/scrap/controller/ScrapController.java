package com.project.fri.scrap.controller;

import com.project.fri.scrap.dto.CreateScrabRequest;
import com.project.fri.scrap.dto.CreateScrabResponse;
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

    @PostMapping
    public ResponseEntity<CreateScrabResponse> createScrab(
            @RequestBody CreateScrabRequest createScrabRequest,
            @RequestHeader("Authorization") Long userId) {
        CreateScrabResponse result = scrapService.createScrab(createScrabRequest, userId);
        // null 리턴 받으면 이미 스크랩한 상태 잘못된요청에 현재 스크랩 상태를 알려주기위헤 ture 반환
        if (result == null) {
            return ResponseEntity.badRequest().body(new CreateScrabResponse(true));
        }

        return ResponseEntity.ok().body(result);
    }
}
