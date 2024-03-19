package com.example.backend.dtos.Booking;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingFilter {
    private String bookingId;
    private String userId;
    private String spaceId;
    private String paymentId;
    private String startDate;
    private String endDate;
    private String owner;
    private String status;
}
