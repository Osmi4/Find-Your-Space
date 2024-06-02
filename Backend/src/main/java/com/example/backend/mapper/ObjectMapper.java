package com.example.backend.mapper;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.dtos.Message.AddMessage;
import com.example.backend.dtos.Message.MessageResponse;
import com.example.backend.dtos.Rating.AddRatingRequest;
import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportResponse;
import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.*;
import com.example.backend.enums.Availibility;
import com.example.backend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Component
public class ObjectMapper {
    public static List<Pair<Date, Date>> mapBookingsToBookedDates(List<Booking> bookings) {
        List<Pair<Date, Date>> bookedDates = new ArrayList<>();
        for (Booking booking : bookings) {
            bookedDates.add(new Pair<>(booking.getStartDateTime(), booking.getEndDateTime()));
        }
        return bookedDates;
    }
}
