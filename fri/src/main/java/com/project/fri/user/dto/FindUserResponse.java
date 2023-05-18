package com.project.fri.user.dto;

import com.project.fri.common.entity.Category;
import com.project.fri.user.entity.User;
import lombok.Data;

@Data
public class FindUserResponse {
    private String name;
    private String email;
    private String nickname;
    private Category area;
    private String year;
    private int heart;
    private boolean isMajor;
    private String anonymousProfileImageId;
    private String roomId;
    private String gameRoomId;
    private boolean emailAgreement;

    public FindUserResponse(User user, boolean emailAgreement) {
        name = user.getName();
        email = user.getEmail();
        nickname = user.getNickname();
        area = user.getArea().getCategory();
        year = user.getYear();
        heart = user.getHeart();
        isMajor = user.isMajor();
        anonymousProfileImageId = user.getAnonymousProfileImage().getImageUrl();
        this.emailAgreement = emailAgreement;
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
