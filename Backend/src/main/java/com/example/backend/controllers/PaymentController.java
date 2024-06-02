package com.example.backend.controllers;

import com.example.backend.dtos.Payment.PaymentRequest;
import com.example.backend.service.PaymentService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private String currency="USD";
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/charge")
    public ResponseEntity<String> chargeCard(@Valid @RequestBody PaymentRequest paymentRequest) {
        try {

           String chargeId = paymentService.addPayment(paymentRequest, currency);
            return ResponseEntity.ok("Payment successful: " + chargeId);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed: " + e.getMessage());
        }
    }
}
