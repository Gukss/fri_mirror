package com.project.fri.user.dto;

import com.project.fri.common.entity.Category;
import com.project.fri.user.entity.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class SignInUserResponse {
    private Long userId;
    private String anonymousProfileImageUrl;
    private Category location;
    private int heart;
    private String roomId;
    private String gameRoomId;

    public SignInUserResponse(User user) {
        userId = user.getId();
        anonymousProfileImageUrl = user.getAnonymousProfileImage().getImageUrl();
        location = user.getArea().getCategory();
        heart = user.getHeart();

        if (user.getRoom() == null) {
            roomId = "참여한 방이 없습니다.";
        } else {
            roomId = user.getRoom().getId().toString();
        }

        if (user.getGameRoom() == null) {
            gameRoomId = "참여한 방이 없습니다.";
        } else {
            gameRoomId = user.getGameRoom().getId().toString();
        }
    }
}
