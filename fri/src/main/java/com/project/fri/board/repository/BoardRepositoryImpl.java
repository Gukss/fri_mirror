package com.project.fri.board.repository;

import com.project.fri.board.dto.ReadBoardAndCommentListResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

/**
 * packageName    : com.project.fri.board.repository fileName       : BoardRepositoryImpl date
 * : 2023-05-11 description    :
 */
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{
  private final JPAQueryFactory queryFactory;

  @Override
  public ReadBoardAndCommentListResponse readBoardAndCommentList(long boardId) {
    return null;
  }
}
