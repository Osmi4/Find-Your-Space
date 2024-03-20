package com.example.backend.dtos.Booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBookingRequest {
    private String spaceId;
    private String startDate;
    private String endDate;

}
