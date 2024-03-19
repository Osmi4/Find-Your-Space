package com.example.backend.dtos.Payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePaymentRequest {
    private String paymentId;
    private double amount;
    private String date;
    private String paymentStatus;
    private String receiver;
    private String sender;
}
