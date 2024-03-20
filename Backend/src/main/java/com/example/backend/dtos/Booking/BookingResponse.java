package com.example.backend.dtos.Booking;

import com.example.backend.dtos.User.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
