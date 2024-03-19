package com.example.backend.dtos.Booking;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditBookingRequest {
    private String bookingId;
    private String spaceId;
    private String startDate;
    private String endDate;
}
