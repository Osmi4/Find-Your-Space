package com.example.backend.dtos.Payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFilter {
    private String paymentId;
    private String paymentStatus;
    private String startDate;
    private String endDate;
    private String receiver;
    private String sender;
    private String amount;
}
