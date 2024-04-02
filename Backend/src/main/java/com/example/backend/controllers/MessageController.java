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
        try{
            return ResponseEntity.ok(messageService.getMyMessages());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();

        }
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
        try {
            return ResponseEntity.ok(messageService.deleteMessage(messageId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping()
    public ResponseEntity<MessageResponse> addMessage(@RequestBody AddMessage message) {
        try {
            return ResponseEntity.ok(messageService.addMessage(message));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<MessageResponse>> getMessagesByUserId(@PathVariable String userId) {
        try {
            return ResponseEntity.ok(messageService.getMessagesByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
