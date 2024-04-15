package com.example.backend.service;

import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;

import java.util.List;

public interface MessageService {
    MessageResponse addMessage(AddMessage message);

    MessageResponse getMessage(String id);

    MessageResponse deleteMessage(String id);

    List<MessageResponse> getMyMessages();

    List<MessageResponse> getMessagesByUserId(String userId);

    MessageResponse updateMessage(String messageId, String message);
}
