package com.project.fri.gameChatting.service;

import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
import com.project.fri.gameChatting.dto.CreateGameChattingMessageRequest;
import com.project.fri.gameChatting.dto.FindGameChattingMessageResponse;
import com.project.fri.gameChatting.entity.GameChattingMessage;
import com.project.fri.gameChatting.repository.GameChattingRepository;
import com.project.fri.room.entity.Room;
import com.project.fri.room.repository.RoomRepository;
import com.project.fri.user.entity.User;
import com.project.fri.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class GameChattingServiceImpl implements GameChattingService {

  private final GameChattingRepository gameChattingRepositroy;
  private final UserRepository userRepository;
  private final RoomRepository roomRepository;
  @Override
  @Transactional
  public void createGameChatting(CreateGameChattingMessageRequest request, Long userId) {
    User user=userRepository.findById(userId)
        .orElseThrow(()->new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    Room room=roomRepository.findById(request.getGameRoomId())
        .orElseThrow(()->new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM));

    GameChattingMessage gameChattingMessage = GameChattingMessage.create(request, user, room);

    gameChattingRepositroy.save(gameChattingMessage);
  }

  @Override
  public List<FindGameChattingMessageResponse> findGameChatting(Long roomId) {

    List<FindGameChattingMessageResponse> responses= gameChattingRepositroy.findGameChattingMessageAndUser(
        roomId);

    return responses;
  }
}
