package com.example.backend.service.impl;

import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.dtos.Payment.AddPaymentRequest;
import com.example.backend.dtos.Payment.PaymentFilter;
import com.example.backend.dtos.Payment.PaymentResponse;
import com.example.backend.dtos.Payment.UpdatePaymentRequest;
import com.example.backend.service.PaymentService;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceImplTest {

    @Test
    void testAddPayment() {
        // Mock dependencies
        PaymentService paymentService = mock(PaymentService.class);
        AddPaymentRequest addPaymentRequest = Instancio.create(AddPaymentRequest.class);

        PaymentResponse expectedResponse = new PaymentResponse();
        expectedResponse.setPaymentId(123L);
        expectedResponse.setAmount(addPaymentRequest.getAmount());
        expectedResponse.setDate(addPaymentRequest.getDate());
        expectedResponse.setPaymentStatus(addPaymentRequest.getPaymentStatus());

        when(paymentService.addPayment(addPaymentRequest)).thenReturn(expectedResponse);

        PaymentResponse actualResponse = paymentService.addPayment(addPaymentRequest);

        // Assertion
        assertNotNull(actualResponse); // Ensure it's not null
        assertEquals(expectedResponse.getPaymentId(), actualResponse.getPaymentId());
        assertEquals(expectedResponse.getAmount(), actualResponse.getAmount());
        assertEquals(expectedResponse.getDate(), actualResponse.getDate());
        assertEquals(expectedResponse.getPaymentStatus(), actualResponse.getPaymentStatus());
        // Additional sender and receiver assertions can be added as needed
    }

    @Test
    void testGetPayment() {
        // Mock dependencies
        PaymentService paymentService = mock(PaymentService.class);
        PaymentResponse expectedResponse = new PaymentResponse();
        expectedResponse.setPaymentId(123L);

        when(paymentService.getPayment("123")).thenReturn(expectedResponse);

        // Test
        PaymentResponse actualResponse = paymentService.getPayment("123");

        // Assertion
        assertNotNull(actualResponse); // Ensure it's not null
        assertEquals(expectedResponse.getPaymentId(), actualResponse.getPaymentId());

        // Test
        when(paymentService.getPayment("999")).thenReturn(null);
        PaymentResponse nonExistentResponse = paymentService.getPayment("999");

        // Assertion
        assertNull(nonExistentResponse);
    }

    @Test
    void testUpdatePayment() {
        // Mock dependencies
        PaymentService paymentService = mock(PaymentService.class);
        UpdatePaymentRequest updatePaymentRequest = new UpdatePaymentRequest();
        updatePaymentRequest.setPaymentId("123");
        updatePaymentRequest.setAmount(150.0);
        updatePaymentRequest.setDate("2024-03-25");
        updatePaymentRequest.setPaymentStatus("Paid");
        updatePaymentRequest.setSender("sender@example.com");
        updatePaymentRequest.setReceiver("receiver@example.com");

        PaymentResponse expectedResponse = new PaymentResponse();
        expectedResponse.setPaymentId(Long.parseLong(updatePaymentRequest.getPaymentId()));
        expectedResponse.setAmount(updatePaymentRequest.getAmount());
        expectedResponse.setDate(updatePaymentRequest.getDate());
        expectedResponse.setPaymentStatus(updatePaymentRequest.getPaymentStatus());

        when(paymentService.updatePayment(updatePaymentRequest)).thenReturn(expectedResponse);

        PaymentResponse actualResponse = paymentService.updatePayment(updatePaymentRequest);

        // Assertion:
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getPaymentId(), actualResponse.getPaymentId());
        assertEquals(expectedResponse.getAmount(), actualResponse.getAmount());
        assertEquals(expectedResponse.getDate(), actualResponse.getDate());
        assertEquals(expectedResponse.getPaymentStatus(), actualResponse.getPaymentStatus());

        // Test
        UpdatePaymentRequest nonExistentUpdate = new UpdatePaymentRequest();
        nonExistentUpdate.setPaymentId("999");
        when(paymentService.updatePayment(nonExistentUpdate)).thenReturn(null);

        PaymentResponse nonExistentUpdateResponse = paymentService.updatePayment(nonExistentUpdate);

        // Assertion for null response
        assertNull(nonExistentUpdateResponse); // Expect null response
    }

    @Test
    void testGetPayments() {
        // Mock dependencies
        PaymentService paymentService = mock(PaymentService.class);
        PaymentFilter paymentFilter = new PaymentFilter();
        List<PaymentResponse> expectedResponse = new ArrayList<>();

        // Test
        List<PaymentResponse> actualResponse = paymentService.getPayments(paymentFilter);

        // Assertion
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.size(), actualResponse.size());
    }
}
