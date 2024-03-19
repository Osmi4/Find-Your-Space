package com.example.backend.service;

import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;

public interface MessageService {
    MessageResponse addMessage(AddMessage message);

    MessageResponse getMessage(String id);

    MessageResponse deleteMessage(String id);
}
