package com.example.backend.dtos.Booking;

import com.example.backend.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BookingFilter {
    private Date startDate;
    private Date endDate;
    private String spaceId;
    private String ownerId;
    private Status status;
}
