package com.project.fri.likes.controller;

import com.project.fri.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
@Slf4j
public class LikesController {
    private final LikesService likesService;

    @PostMapping
    public ResponseEntity<?> createLikes() {

        return null;
    }

}
