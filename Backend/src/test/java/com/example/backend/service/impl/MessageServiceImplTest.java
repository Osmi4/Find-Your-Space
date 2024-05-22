package com.example.backend.service.impl;

import com.example.backend.auth.AuthenticationResponse;
import com.example.backend.auth.AuthenticationService;
import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.entity.User;
import com.example.backend.enums.SpaceType;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.MessageService;
import jakarta.transaction.Transactional;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MessageServiceImplTest{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private MessageService messageService;
    private AddMessage addMessage;
    private RegisterDto registerDto;
    private RegisterDto registerDto2;
    @BeforeEach
    public void setUp() {
        registerDto2 = Instancio.create(RegisterDto.class);
        registerDto = Instancio.create(RegisterDto.class);
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto);
        User user = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        AuthenticationResponse authenticationResponse2 = authenticationService.register(registerDto2);
        User user2 = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        addMessage = new AddMessage();
        assert user2 != null;
        addMessage.setReceiverId(user2.getUserId());
        addMessage.setMessageContent("messageContent");
        addMessage.setMessageDestinationEmail(user2.getEmail());
    }

    @Test
    void addMessage_Success() {
        // Given
        // When
        MessageResponse messageResponse = messageService.addMessage(addMessage);
        // Then
        assertNotNull(messageResponse);
        assertEquals(messageResponse.getMessageContent(), addMessage.getMessageContent());
        assertEquals(messageResponse.getMessageDestinationEmail(), addMessage.getMessageDestinationEmail());
        assertEquals(messageResponse.getReceiverId(), addMessage.getReceiverId());
    }
    @Test
    void getMessage_Success() {
        // Given
        MessageResponse messageResponse = messageService.addMessage(addMessage);
        // When
        MessageResponse messageResponse1 = messageService.getMessage(messageResponse.getMessageId());
        // Then
        assertNotNull(messageResponse1);
        assertEquals(messageResponse1.getMessageContent(), addMessage.getMessageContent());
        assertEquals(messageResponse1.getMessageDestinationEmail(), addMessage.getMessageDestinationEmail());
        assertEquals(messageResponse1.getReceiverId(), addMessage.getReceiverId());
    }

    @Test
    void deleteMessage_Success() {
        // Given
        MessageResponse messageResponse = messageService.addMessage(addMessage);
        // When
        MessageResponse messageResponse1 = messageService.deleteMessage(messageResponse.getMessageId());
        // Then
        assertNotNull(messageResponse1);
        assertEquals(messageResponse1.getMessageContent(), addMessage.getMessageContent());
        assertEquals(messageResponse1.getMessageDestinationEmail(), addMessage.getMessageDestinationEmail());
        assertEquals(messageResponse1.getReceiverId(), addMessage.getReceiverId());
    }

    @Test
    void getMyMessages_Success() {
        // Given
        MessageResponse messageResponse = messageService.addMessage(addMessage);
        // When
        Pageable pageable = PageRequest.of(0, 10);

        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        Page<MessageResponse> messageResponses = messageService.getMyMessages(pageable);
        // Then
        assertNotNull(messageResponses);
        assertEquals(messageResponses.getContent().size(), 1);
    }

    @Test
    void getMessagesByUserId_Success() {
        // Given
        MessageResponse messageResponse = messageService.addMessage(addMessage);
        // When
        Pageable pageable = PageRequest.of(0, 10);
        String userId = userRepository.findByEmail(registerDto2.getEmail()).orElse(null).getUserId();
        Page<MessageResponse> messageResponses = messageService.getMessagesByUserId(userId, pageable);
        // Then
        assertNotNull(messageResponses);
        assertEquals(messageResponses.getContent().size(), 1);
    }

    @Test
    void UpdateMessage_Success() {
        // Given
        MessageResponse messageResponse = messageService.addMessage(addMessage);
        // When
        MessageResponse messageResponse1 = messageService.updateMessage(messageResponse.getMessageId(), "new message");
        // Then
        assertNotNull(messageResponse1);
        assertEquals(messageResponse1.getMessageContent(), "messageContent");
    }




}
