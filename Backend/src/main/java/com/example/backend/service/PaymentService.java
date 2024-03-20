package com.example.backend.service;

import com.example.backend.dtos.Payment.AddPaymentRequest;
import com.example.backend.dtos.Payment.PaymentFilter;
import com.example.backend.dtos.Payment.PaymentResponse;
import com.example.backend.dtos.Payment.UpdatePaymentRequest;

import java.util.List;

public interface PaymentService {
    PaymentResponse addPayment(AddPaymentRequest addPaymentRequest);

    PaymentResponse getPayment(String id);

    PaymentResponse updatePayment(UpdatePaymentRequest updatePaymentRequest);

    List<PaymentResponse> getPayments(PaymentFilter paymentFilter);
}
