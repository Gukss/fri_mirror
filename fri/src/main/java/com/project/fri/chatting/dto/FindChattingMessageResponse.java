package com.project.fri.chatting.dto;

import com.project.fri.chatting.entity.ChattingMessage;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FindChattingMessageResponse {
  private String message;
  private Long userId;
  private String nickname;
  private String anonymousProfileImageUrl;
  private boolean isMajor;
  private String year;
  private LocalDateTime createdAt;

  @QueryProjection
  public FindChattingMessageResponse(ChattingMessage chattingMessage){
    this.message=chattingMessage.getMessage();
    this.userId=chattingMessage.getUser().getId();
    this.nickname=chattingMessage.getUser().getNickname();
    this.anonymousProfileImageUrl=chattingMessage.getUser().getAnonymousProfileImage().getImageUrl();
    this.isMajor=chattingMessage.getUser().isMajor();
    this.year=chattingMessage.getUser().getYear();
    this.createdAt=chattingMessage.getUpdatedAt();
  }
}
