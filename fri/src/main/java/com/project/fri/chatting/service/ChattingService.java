package com.project.fri.chatting.service;


import com.project.fri.chatting.dto.CreateChattingMessageRequest;
import com.project.fri.chatting.dto.FindChattingMessageResponse;
import com.project.fri.user.entity.User;
import java.util.List;

public interface ChattingService {
    void createChatting(CreateChattingMessageRequest request,Long userId);
    List<FindChattingMessageResponse> findChatting(Long roomId, Long userId);
}
