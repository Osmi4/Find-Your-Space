package com.example.backend.dtos.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddMessage {
    private String messageContent;
    private String messageDestinationEmail;
    private String sender;
    private String receiver;
}
