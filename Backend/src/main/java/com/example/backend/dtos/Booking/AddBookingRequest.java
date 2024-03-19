package com.example.backend.dtos.Booking;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddBookingRequest {
    private String spaceId;
    private String startDate;
    private String endDate;

}
