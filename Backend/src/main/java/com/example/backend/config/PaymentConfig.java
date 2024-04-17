package com.example.backend.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    @Value("${stripe.secretKey}")
    private String secretKey;

    @Bean
    public String initPayment() {
        Stripe.apiKey = secretKey;
        return Stripe.apiKey;
    }
}