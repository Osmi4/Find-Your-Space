package com.example.backend.dtos.Booking;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EditBookingRequest {
    private String spaceId;
    private Date startDate;
    private Date endDate;
}
