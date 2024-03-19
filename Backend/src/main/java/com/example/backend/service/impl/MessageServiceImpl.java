package com.example.backend.service.impl;

import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import com.example.backend.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public MessageResponse addMessage(AddMessage message) {
        return null;
    }

    @Override
    public MessageResponse getMessage(String id) {
        return null;
    }

    public MessageResponse deleteMessage(String id) {
        return null;
    }
}
