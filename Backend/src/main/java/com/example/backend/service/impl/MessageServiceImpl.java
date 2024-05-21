package com.example.backend.service.impl;

import com.example.backend.autoMapper.MessageMapper;
import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import com.example.backend.entity.Message;
import com.example.backend.entity.User;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.MessageService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "user not log in");
        }
        Optional<User> receiver = userRepository.findByUserId(message.getReceiverId());
        if(receiver.isEmpty()) {
            throw new ResourceNotFoundException("Receiver not found", "user", message.getReceiverId());
        }
        Message addedMessage = messageRepository.save(MessageMapper.INSTANCE.mapAddMessageToMessage(message, sender, receiver.get()));
        return MessageMapper.INSTANCE.mapMessageToMessageResponse(addedMessage);
    }

    @Override
    public MessageResponse getMessage(String id) {
        Optional<Message> message = messageRepository.findByMessageId(id);
        if(message.isEmpty()) {
            throw new ResourceNotFoundException("Message not found", "space", id);
        }
        return MessageMapper.INSTANCE.mapMessageToMessageResponse(message.get());
    }
    @Override
    @Transactional
    public MessageResponse deleteMessage(String id) {
        Optional<Message> message = messageRepository.findByMessageId(id);
        if(message.isEmpty()) {
            throw new ResourceNotFoundException("Message not found", "space", id);
        }
        int deleted = messageRepository.deleteByMessageId(id);
        if(deleted == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "message not deleted");
        }
        return MessageMapper.INSTANCE.mapMessageToMessageResponse(message.get());
    }

    @Override
    public Page<MessageResponse> getMyMessages(Pageable pageable) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "user not log in");
        }
        return messageRepository.findByReceiver_UserId(user.getUserId(), pageable).map(MessageMapper.INSTANCE::mapMessageToMessageResponse);
    }

    @Override
    public Page<MessageResponse> getMessagesByUserId(String userId, Pageable pageable) {
        Page<Message> messages = messageRepository.findByReceiver_UserIdOrderByMessageDateTimeAsc(userId , pageable);
        return messageRepository.findByReceiver_UserIdOrderByMessageDateTimeAsc(userId, pageable).map(MessageMapper.INSTANCE::mapMessageToMessageResponse);
    }

    @Override
    public MessageResponse updateMessage(String messageId, String message) {
        Optional<Message> messageOptional = messageRepository.findByMessageId(messageId);
        if(messageOptional.isEmpty()) {
            throw new ResourceNotFoundException("Message not found", "space", messageId);
        }
        int affectedRows = messageRepository.updateMessage(messageId, message);
        if(affectedRows == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "message not updated");
        }
        Message updatedMessage = messageRepository.findByMessageId(messageId).get();
        return MessageMapper.INSTANCE.mapMessageToMessageResponse(updatedMessage);
    }


}
