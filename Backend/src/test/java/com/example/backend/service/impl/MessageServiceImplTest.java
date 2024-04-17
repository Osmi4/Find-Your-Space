package com.example.backend.service.impl;

import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import com.example.backend.entity.Message;
import com.example.backend.entity.User;
import com.example.backend.enums.Role;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private MessageServiceImpl messagingService;

    private User sender;
    private User receiver;
    private Message message;
    private List<Message> messages;

    @BeforeEach
    public void setUp() {
        sender = new User();
        sender.setUserId("1");
        sender.setEmail("sender@example.com");

        receiver = new User();
        receiver.setUserId("2");
        receiver.setEmail("receiver@example.com");

        message = new Message();
        message.setMessageId("1");
        message.setMessageContent("Hello, this is a test message");
        message.setSender(sender);
        message.setReceiver(receiver);
        messages = new ArrayList<>();
        messages.add(message);

    }
    @BeforeEach
    public void setUpSecurityContext() {
        User mockUser = User.builder()
                .userId("1")
                .role(Role.USER)
                .password("password")
                .email("test@gmail.com")
                .firstName("John")
                .lastName("Doe").build();
        Authentication auth = new UsernamePasswordAuthenticationToken(mockUser, null);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.lenient().when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }
    @Test
    public void testAddMessage_Success() {
        AddMessage addMessage = new AddMessage("Hello", "receiver@example.com", "user1", "user2");

        when(userRepository.findByUserId("user2")).thenReturn(Optional.of(receiver));
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MessageResponse result = messagingService.addMessage(addMessage);

        assertNotNull(result);
        assertEquals("Hello", result.getMessageContent());
        assertEquals("1", result.getSenderId());
        assertEquals("2", result.getReceiverId());
        verify(messageRepository).save(any(Message.class));
    }
    @Test
    public void testGetMessage_Success() {
        when(messageRepository.findByMessageId("1")).thenReturn(Optional.of(message));

        MessageResponse result = messagingService.getMessage("1");

        assertNotNull(result);
        assertEquals("1", result.getMessageId());
        assertEquals("Hello, this is a test message", result.getMessageContent());

        verify(messageRepository).findByMessageId("1");
    }

    @Test
    public void testDeleteMessage_Success() {
        when(messageRepository.findByMessageId("1")).thenReturn(Optional.of(message));
        when(messageRepository.deleteByMessageId("1")).thenReturn(1);

        MessageResponse result = messagingService.deleteMessage("1");

        assertNotNull(result);
        assertEquals("1", result.getMessageId());
        assertEquals("Hello, this is a test message", result.getMessageContent());

        verify(messageRepository).deleteByMessageId("1");
    }

    @Test
    public void testGetMyMessages_Success() {
        when(messageRepository.findByReceiver_UserId("1")).thenReturn(messages);

        List<MessageResponse> results = messagingService.getMyMessages();

        assertNotNull(results);
        assertEquals(1, results.size()); // Ensure the correct number of messages is returned
        assertEquals("Hello, this is a test message", results.get(0).getMessageContent()); // Check content of the message

        verify(messageRepository).findByReceiver_UserId("1");
    }
    @Test
    public void testGetMessagesByUserId_Success() {
        when(messageRepository.findByReceiver_UserIdOrderByMessageDateTimeAsc("1")).thenReturn(messages);
        List<MessageResponse> results = messagingService.getMessagesByUserId("1");
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("1", results.get(0).getMessageId());
        assertEquals("Hello, this is a test message", results.get(0).getMessageContent());
        verify(messageRepository).findByReceiver_UserIdOrderByMessageDateTimeAsc("1");
    }
    @Test
    public void testUpdateMessage_Success() {
        when(messageRepository.findByMessageId("1")).thenReturn(Optional.of(message));
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MessageResponse result = messagingService.updateMessage("1", "Updated content");

        assertNotNull(result);
        assertEquals("1", result.getMessageId());
        assertEquals("Updated content", result.getMessageContent());
        assertNotNull(result.getMessageDateTime()); // Ensure the date is updated
        assertNotEquals("Old content", result.getMessageContent());

        verify(messageRepository).findByMessageId("1");
        verify(messageRepository).save(message);
    }

}
