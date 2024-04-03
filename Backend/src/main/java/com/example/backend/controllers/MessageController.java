package com.example.backend.controllers;

import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import com.example.backend.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/message")
public class MessageController {
    private final MessageService messageService;
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/my-messages")
    public ResponseEntity<List<MessageResponse>> getMyMessages() {
        return ResponseEntity.ok(messageService.getMyMessages());
    }
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageResponse> getMessage(@PathVariable String messageId) {
        try {
            return ResponseEntity.ok(messageService.getMessage(messageId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/{messageId}")
    public ResponseEntity<MessageResponse> deleteMessage(@PathVariable String messageId) {
        return ResponseEntity.ok(messageService.deleteMessage(messageId));
    }

    @PostMapping()
    public ResponseEntity<MessageResponse> addMessage(@RequestBody AddMessage message) {
        return ResponseEntity.ok(messageService.addMessage(message));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<MessageResponse>> getMessagesByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(messageService.getMessagesByUserId(userId));
    }
}
