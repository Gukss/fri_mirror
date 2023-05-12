package com.project.fri.likes.service;

import com.project.fri.likes.dto.CreateLikesRequest;
import com.project.fri.likes.dto.CreateLikesResponse;
import com.project.fri.likes.dto.UpdateLikesRequest;
import com.project.fri.likes.dto.UpdateLikesResponse;

public interface LikesService {

    /**
     * 좋아요 생성
     * @param createLikesRequest 요청
     * @param userId Authorization
     * @return 응답
     */
    CreateLikesResponse createLikes(CreateLikesRequest createLikesRequest, Long userId);

    /**
     * 좋아요 취소
     * @param updateLikesRequest 요청
     * @param userId Authorization
     * @return 응답
     */
    UpdateLikesResponse updateLikes(UpdateLikesRequest updateLikesRequest, Long userId);

}
