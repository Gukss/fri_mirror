package com.project.fri.chatting.service;


import com.project.fri.chatting.dto.CreateChattingRequest;
import com.project.fri.chatting.dto.FindChattingMessageResponse;
import com.project.fri.chatting.entity.ChattingMessage;
import com.project.fri.chatting.repository.ChattingRepository;
import com.project.fri.exception.exceptino_message.NotFoundExceptionMessage;
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
public class ChattingServiceImpl implements ChattingService{
  private final ChattingRepository chattingRepository;
  private final UserRepository userRepository;
  private final RoomRepository roomRepository;
  @Override
  @Transactional
  public void createChatting(CreateChattingRequest request,Long userId) {
    User user=userRepository.findById(userId)
        .orElseThrow(()->new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_USER));

    Room room=roomRepository.findById(request.getRoomId())
            .orElseThrow(()->new NotFoundExceptionMessage(NotFoundExceptionMessage.NOT_FOUND_ROOM));

    ChattingMessage chattingMessage = ChattingMessage.create(request, user, room);

    chattingRepository.save(chattingMessage);
  }

  @Override
  public List<FindChattingMessageResponse> findChatting(Long roomId) {

    List<FindChattingMessageResponse> chattingMessageAndUser = chattingRepository.findChattingMessageAndUser(
        roomId);

    return chattingMessageAndUser;
  }
}
