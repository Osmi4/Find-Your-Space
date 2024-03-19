package com.example.backend.dtos.Payment;

import com.example.backend.dtos.User.UserResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {
    private Long paymentId;
    private double amount;
    private String date;
    private String paymentStatus;
    private UserResponse receiver;
    private UserResponse sender;

}
