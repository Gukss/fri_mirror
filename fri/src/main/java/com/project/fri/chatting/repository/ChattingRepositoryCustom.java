package com.project.fri.chatting.repository;

import com.project.fri.chatting.dto.FindChattingMessageResponse;
import com.project.fri.chatting.entity.ChattingMessage;
import com.project.fri.room.entity.Room;
import java.util.List;

public interface ChattingRepositoryCustom {
  List<FindChattingMessageResponse> findChattingMessageAndUser(Long roomId);
}
