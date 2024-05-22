package com.example.backend.controllers;

import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import com.example.backend.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/message")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {
    private final MessageService messageService;
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/my-messages")
    public ResponseEntity<Page<MessageResponse>> getMyMessages(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(messageService.getMyMessages(pageable));
    }
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageResponse> getMessage(@PathVariable String messageId) {
        try {
            return ResponseEntity.ok(messageService.getMessage(messageId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{messageId}")
    public ResponseEntity<MessageResponse> deleteMessage(@PathVariable String messageId) {
        return ResponseEntity.ok(messageService.deleteMessage(messageId));
    }
    @PostMapping()
    public ResponseEntity<MessageResponse> addMessage(@RequestBody AddMessage message) {
        return ResponseEntity.ok(messageService.addMessage(message));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Page<MessageResponse>> getMessagesByUserId(@PathVariable String userId, @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(messageService.getMessagesByUserId(userId , pageable));
    }
    @PutMapping("/{messageId}")
    public ResponseEntity<MessageResponse> updateMessage(@PathVariable String messageId, @RequestBody String message) {
        return ResponseEntity.ok(messageService.updateMessage(messageId, message));
    }
}
