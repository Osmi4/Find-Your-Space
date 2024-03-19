package com.example.backend.service;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingResponse;

public interface BookingService {
    BookingResponse getBooking(String id);
    BookingResponse addBooking(AddBookingRequest addBookingRequest);
    BookingResponse updateBooking(AddBookingRequest addBookingRequest);
    BookingResponse deleteBooking(String id);
}
