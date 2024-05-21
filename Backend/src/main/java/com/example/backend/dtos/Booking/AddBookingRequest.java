package com.example.backend.dtos.Booking;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Space ID cannot be null")
    private String spaceId;

    @Future(message = "Start date must be in the future")
    @NotBlank(message = "Start date cannot be null")
    private Date startDateTime;

    @Future(message = "End date must be in the future")
    @NotBlank(message = "End date cannot be null")
    private Date endDateTime;

    private String description;
    //w userze dodac zeby wziac jego contact info
}
