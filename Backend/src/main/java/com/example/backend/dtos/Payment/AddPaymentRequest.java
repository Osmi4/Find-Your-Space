package com.example.backend.dtos.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddPaymentRequest {
    private double amount;
    private String date;
    private String paymentStatus;
    private String receiver;
    private String sender;
}
