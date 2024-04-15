package com.example.backend.dtos.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private String messageId;
    private String messageContent;
    private Date messageDateTime;
    private String messageDestinationEmail;
    private String senderId;
    private String receiverId;
}
