package com.example.backend.service;

import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    MessageResponse addMessage(AddMessage message);

    MessageResponse getMessage(String id);

    MessageResponse deleteMessage(String id);

    Page<MessageResponse> getMyMessages(Pageable pageable);

    Page<MessageResponse> getMessagesByUserId(String userId, Pageable pageable);

    MessageResponse updateMessage(String messageId, String message);
}
