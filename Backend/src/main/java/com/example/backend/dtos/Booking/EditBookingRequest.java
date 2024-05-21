package com.example.backend.dtos.Booking;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EditBookingRequest {
    @Future(message = "Start date must be in the future")
    @Nullable
    private Date startDate;
    @Future(message = "End date must be in the future")
    @Nullable
    private Date endDate;
}
