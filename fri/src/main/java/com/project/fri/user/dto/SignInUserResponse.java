package com.project.fri.user.dto;

import com.project.fri.common.entity.Category;
import com.project.fri.user.entity.User;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class SignInUserResponse {
    private Long userId;
    private Category location;
    private int heart;
    private Long roomId;
    private Long gameRoomId;

    public SignInUserResponse(User user) {
        userId = user.getId();
        location = user.getArea().getCategory();
        heart = user.getHeart();

        if (user.getRoom() == null) {
            roomId = null;
        } else {
            roomId = user.getRoom().getId();
        }

        if (user.getGameRoom() == null) {
            gameRoomId = null;
        } else {
            gameRoomId = user.getGameRoom().getId();
        }
    }
}

/*
    "userId": 1,
	"location": "SEOUL",
	"heart": 5,
	"roomId" : 1,
	"gameRoomId" : "참여한 방이 없습니다" -> 어쩔 수 없이 String 으로 선언
 */
