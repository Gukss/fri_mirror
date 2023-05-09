package com.project.fri.gameChatting.repository;

import com.project.fri.chatting.entity.QChattingMessage;
import com.project.fri.gameChatting.dto.FindGameChattingMessageResponse;
import com.project.fri.gameChatting.dto.QFindGameChattingMessageResponse;
import com.project.fri.gameChatting.entity.QGameChattingMessage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameChattingRepositoryImpl implements GameChattingRepositoryCustom{
  private final JPAQueryFactory queryFactory;
  @Override
  public List<FindGameChattingMessageResponse> findGameChattingMessageAndUser(Long roomId,
      LocalDateTime gameRoomCreatedTime) {
    return queryFactory.select(new QFindGameChattingMessageResponse(QGameChattingMessage.gameChattingMessage))
        .from(QGameChattingMessage.gameChattingMessage)
        .join(QGameChattingMessage.gameChattingMessage.user).fetchJoin()
        .where(QGameChattingMessage.gameChattingMessage.gameRoom.id.eq(roomId)
            .and(QGameChattingMessage.gameChattingMessage.createdAt.after(gameRoomCreatedTime)))
        .orderBy(QGameChattingMessage.gameChattingMessage.createdAt.asc())
        .fetch();

  }
}
