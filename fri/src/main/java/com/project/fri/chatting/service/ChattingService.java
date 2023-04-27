package com.project.fri.chatting.service;


import com.project.fri.chatting.dto.CreateChattingRequest;
import com.project.fri.chatting.dto.FindChattingMessageResponse;
import java.util.List;

public interface ChattingService {
    void createChatting(CreateChattingRequest request,Long userId);
    List<FindChattingMessageResponse> findChatting(Long roomId);
}
