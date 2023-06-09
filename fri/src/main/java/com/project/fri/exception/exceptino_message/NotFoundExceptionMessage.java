package com.project.fri.exception.exceptino_message;

/**
 * packageName    : com.project.fri.exception.exceptino_message fileName       :
 * NotFoundExceptionMessage date           : 2023-04-19 description    :
 */
public class NotFoundExceptionMessage extends RuntimeException{
  public static final String NOT_FOUND_ROOM_LIST = "존재하지 않는 방 리스트 입니다.";
  public static final String NOT_FOUND_USER = "존재하지 않는 회원입니다.";
  public static final String NOT_FOUND_ROOM = "존재하지 않는 방입니다.";
  public static final String NOT_FOUND_AREA = "존재하지 않는 지역입니다.";
  public static final String NOT_FOUND_ROOM_CATEGORY = "존재하지 않는 지역입니다.";
  public static final String NOT_FOUND_CERTIFICATION = "존재하지 않는 검증입니다.";
  public static final String NOT_FOUND_AnonymousProfileImage = "존재하지 않는 이미지입니다.";
  public static final String NOT_FOUND_BOARD="존재하지 않는 게시판입니다.";
  public static final String NOT_FOUND_COMMENT="존재하지 않는 댓글입니다.";
  public static final String NOT_FOUND_GAME_ROOM = "존재하지 않는 게임 방입니다.";
  public static final String NOT_FOUND_USER_ROOM_TIME = "유저가 참여한 시간 데이터가 없습니다.";
  public static final String NOT_FOUND_SCRAP = "존재하지 않는 스크랩입니다.";
  public static final String NOT_FOUND_CATEGORY = "존재하지 않는 카테고리입니다.";


  public NotFoundExceptionMessage() {
    super();
  }

  public NotFoundExceptionMessage(String message) {
    super(message);
  }
}