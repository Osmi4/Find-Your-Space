package com.example.backend.service.impl;

import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import com.example.backend.entity.Message;
import com.example.backend.entity.User;
import com.example.backend.mapper.ObjectMapper;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.MessageService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MessageResponse addMessage(AddMessage message) {
        User sender = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(sender == null) {
            throw new RuntimeException("User not found");
        }
        Optional<User> receiver = userRepository.findByUserId(message.getReceiverId());
        if(receiver.isEmpty()) {
            throw new RuntimeException("Receiver not found");
        }
        Message addedMessage = messageRepository.save(ObjectMapper.mapAddMessageToMessage(message, sender , receiver.get()));
        return ObjectMapper.mapMessageToMessageResponse(addedMessage);
    }

    @Override
    public MessageResponse getMessage(String id) {
        Optional<Message> message = messageRepository.findByMessageId(id);
        if(message.isEmpty()) {
            throw new RuntimeException("Message not found");
        }
        return ObjectMapper.mapMessageToMessageResponse(message.get());
    }

    public MessageResponse deleteMessage(String id) {
        Optional<Message> message = messageRepository.findByMessageId(id);
        if(message.isEmpty()) {
            throw new RuntimeException("Message not found");
        }
        long deleted = messageRepository.deleteByMessageId(id);
        if(deleted == 0) {
            throw new RuntimeException("Message not found");
        }
        return ObjectMapper.mapMessageToMessageResponse(message.get());
    }

    @Override
    public List<MessageResponse> getMyMessages() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user == null) {
            throw new RuntimeException("User not found");
        }
        List<Message> messages = messageRepository.findByReceiver_UserId(user.getUserId());
        return messages.stream().map(ObjectMapper::mapMessageToMessageResponse).toList();
    }

    @Override
    public List<MessageResponse> getMessagesByUserId(String userId) {
        List<Message> messages = messageRepository.findByReceiver_UserIdOrderByMessageDateTimeAsc(userId);
        return messages.stream().map(ObjectMapper::mapMessageToMessageResponse).toList();
    }
}
