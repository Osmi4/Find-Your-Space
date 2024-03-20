package com.example.backend.service.impl;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingFilter;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.service.BookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Override
    public BookingResponse getBooking(String id) {
        return null;
    }

    @Override
    public BookingResponse addBooking(AddBookingRequest addBookingRequest) {
        return null;
    }

    @Override
    public BookingResponse updateBooking(AddBookingRequest addBookingRequest) {
        return null;
    }

    @Override
    public BookingResponse deleteBooking(String id) {
        return null;
    }

    @Override
    public List<BookingResponse> searchBooking(BookingFilter filter) {
        return null;
    }
}
