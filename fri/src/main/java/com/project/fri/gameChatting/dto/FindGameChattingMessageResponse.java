package com.project.fri.gameChatting.dto;

import com.project.fri.gameChatting.entity.GameChattingMessage;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FindGameChattingMessageResponse {
  private String message;
  private Long userId;
  private String nickname;
  private String anonymousProfileImageUrl;
  private boolean isMajor;
  private String year;
  private LocalDateTime createdAt;

  @QueryProjection
  public FindGameChattingMessageResponse(GameChattingMessage gameChattingMessage){
    this.message=gameChattingMessage.getMessage();
    this.userId=gameChattingMessage.getUser().getId();
    this.nickname=gameChattingMessage.getUser().getNickname();
    this.anonymousProfileImageUrl=gameChattingMessage.getUser().getAnonymousProfileImage().getImageUrl();
    this.isMajor=gameChattingMessage.getUser().isMajor();
    this.year=gameChattingMessage.getUser().getYear();
    this.createdAt=gameChattingMessage.getCreatedAt();
  }
}
