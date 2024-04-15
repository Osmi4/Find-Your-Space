package com.example.backend.dtos.Booking;

import com.example.backend.enums.SortType;
import com.example.backend.enums.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class BookingFilter {
    @Future(message = "Start date must be in the future")
    @NotNull(message = "Start date cannot be null")
    private Date startDate;
    @Future(message = "End date must be in the future")
    @NotNull(message = "End date cannot be null")
    private Date endDate;
    private String clientId;
    private String spaceId; ///opcjonalnie pozniej po liscie
    private String ownerId; //opcjonalie po liscie
    private Status status;
    private SortType sortByDate;
    private SortType sortByPrice;
}
