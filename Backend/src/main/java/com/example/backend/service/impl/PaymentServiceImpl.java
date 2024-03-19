package com.example.backend.service.impl;

import com.example.backend.dtos.Payment.AddPaymentRequest;
import com.example.backend.dtos.Payment.PaymentFilter;
import com.example.backend.dtos.Payment.PaymentResponse;
import com.example.backend.dtos.Payment.UpdatePaymentRequest;
import com.example.backend.service.PaymentService;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Override
    public PaymentResponse addPayment(AddPaymentRequest addPaymentRequest) {
        return null;
    }

    @Override
    public PaymentResponse getPayment(String id) {
        return null;
    }

    @Override
    public PaymentResponse updatePayment(UpdatePaymentRequest updatePaymentRequest) {
        return null;
    }

    @Override
    public List<PaymentResponse> getPayments(PaymentFilter paymentFilter) {
        return null;
    }
}
