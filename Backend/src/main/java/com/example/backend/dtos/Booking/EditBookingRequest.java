package com.example.backend.dtos.Booking;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class EditBookingRequest {
    //wywalic space id zmaist tego usuwamy
    private String spaceId;
    private Date startDate;
    private Date endDate;
    //info tez mozna zmienic
}
