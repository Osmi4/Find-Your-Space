package com.example.backend.service.impl;

import com.example.backend.dtos.Payment.*;
import com.example.backend.entity.Booking;
import com.example.backend.enums.Status;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.ReportRepository;
import com.example.backend.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final BookingRepository bookingRepository;

    public PaymentServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public String addPayment(PaymentRequest paymentRequest,String currency) throws StripeException {
        Booking booking = bookingRepository.findById(paymentRequest.getBookingId()).orElseThrow(() -> new RuntimeException("Booking not found"));
        if(!booking.getStatus().equals(Status.ACCEPTED)){
            throw new RuntimeException("Booking is not accepted");
        }
        if(booking.getCost() != paymentRequest.getAmount()){
            throw new RuntimeException("Amount mismatch");
        }
        ChargeCreateParams createParams = new ChargeCreateParams.Builder()
                .setAmount((long) paymentRequest.getAmount())
                .setCurrency(currency)
                .setSource(paymentRequest.getToken())
                .build();

        Charge charge = Charge.create(createParams);
        // Update booking status
        booking.setStatus(Status.COMPLETED);
        bookingRepository.save(booking);
        return charge.getId();
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
