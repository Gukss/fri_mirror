package com.project.fri.chatting.repository;

import com.project.fri.chatting.dto.FindChattingMessageResponse;
import com.project.fri.chatting.dto.QFindChattingMessageResponse;
import com.project.fri.chatting.entity.ChattingMessage;
import com.project.fri.chatting.entity.QChattingMessage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChattingRepositoryImpl implements ChattingRepositoryCustom {
  private final JPAQueryFactory queryFactory;
  @Override
  public List<FindChattingMessageResponse> findChattingMessageAndUser(Long roomId) {
    return queryFactory.select(new QFindChattingMessageResponse(QChattingMessage.chattingMessage))
        .from(QChattingMessage.chattingMessage)
        .join(QChattingMessage.chattingMessage.user).fetchJoin()
        .where(QChattingMessage.chattingMessage.room.id.eq(roomId))
        .orderBy(QChattingMessage.chattingMessage.createdAt.asc())
        .fetch();
  }
}