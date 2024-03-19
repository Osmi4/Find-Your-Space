package com.example.backend.dtos.Message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {
    private String messageId;
    private String messageContent;
    private String messageDateTime;
    private String messageDestinationEmail;
    private String sender;
    private String receiver;
}
