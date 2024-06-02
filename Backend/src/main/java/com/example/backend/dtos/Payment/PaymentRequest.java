package com.example.backend.dtos.Payment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    @NotBlank(message = "Token cannot be blank")
    private String token;

    private long amount;
    @NotBlank(message = "Booking id cannot be blank")
    private String bookingId;
}
