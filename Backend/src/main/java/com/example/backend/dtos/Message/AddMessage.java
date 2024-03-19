package com.example.backend.dtos.Message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddMessage {
    private String messageContent;
    private String messageDestinationEmail;
    private String sender;
    private String receiver;
}
