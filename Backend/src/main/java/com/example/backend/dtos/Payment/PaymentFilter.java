package com.example.backend.dtos.Payment;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentFilter {
    private String paymentId;
    private String paymentStatus;
    private String startDate;
    private String endDate;
    private String receiver;
    private String sender;
    private String amount;
}
