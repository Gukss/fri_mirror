package com.project.fri.chatting.dto;

import lombok.Data;

// request와 response타입을 동시에 사용해서 이름을 이렇게 지음, 파일 위치는 다시 고려해봐야할듯
@Data
public class SocketChattingRequestAndResponse {
  private Long roomId;
  private String message;
  private Long memberId;
  private String anonymousProfileImageUrl;
  private String nickname;
  private String time;
}
