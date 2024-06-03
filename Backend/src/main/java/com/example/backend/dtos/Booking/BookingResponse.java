package com.example.backend.dtos.Booking;

import com.example.backend.dtos.User.UserResponse;
import com.example.backend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {
    private String bookingId;
    private String description;
    private String spaceId;
    private Date startDateTime;
    private Date endDateTime;
    private Status status;
    private UserResponse client;
    private UserResponse owner;
    private double cost;
    private Date dateAdded;
    private Date dateUpdated;
}
