package com.example.backend.service;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingFilter;
import com.example.backend.dtos.Booking.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse getBooking(String id);

    BookingResponse addBooking(AddBookingRequest addBookingRequest);

    BookingResponse updateBooking(AddBookingRequest addBookingRequest);

    BookingResponse deleteBooking(String id);

    List<BookingResponse> searchBooking(BookingFilter filter);
}
