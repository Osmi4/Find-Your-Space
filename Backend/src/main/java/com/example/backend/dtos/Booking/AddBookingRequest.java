package com.example.backend.dtos.Booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBookingRequest {
    private String spaceId;
    private Date startDate;
    private Date endDate;
}
