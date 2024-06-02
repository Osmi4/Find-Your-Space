package com.example.backend.service;

import com.example.backend.dtos.Payment.*;
import com.stripe.exception.StripeException;

import java.util.List;

public interface PaymentService {
    String addPayment(PaymentRequest addPaymentRequest, String currency) throws StripeException;

    PaymentResponse getPayment(String id);

    PaymentResponse updatePayment(UpdatePaymentRequest updatePaymentRequest);

    List<PaymentResponse> getPayments(PaymentFilter paymentFilter);
}
