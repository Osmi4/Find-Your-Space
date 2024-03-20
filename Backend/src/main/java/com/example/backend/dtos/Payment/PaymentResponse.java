package com.example.backend.dtos.Payment;

import com.example.backend.dtos.User.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private double amount;
    private String date;
    private String paymentStatus;
    private UserResponse receiver;
    private UserResponse sender;

}
