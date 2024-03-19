package com.example.backend.dtos.Booking;

import com.example.backend.dtos.User.UserResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponse {
    private String bookingId;
    private String spaceId;
    private String startDate;
    private String endDate;
    private String status;
    private UserResponse client;
    private UserResponse owner;
}
