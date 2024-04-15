package com.example.backend.dtos.Booking;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Space ID cannot be null")
    private String spaceId;

    @Future(message = "Start date must be in the future")
    @NotNull(message = "Start date cannot be null")
    private Date startDate;

    @Future(message = "End date must be in the future")
    @NotNull(message = "End date cannot be null")
    private Date endDate;

    private String description;
    //w userze dodac zeby wziac jego contact info
}
