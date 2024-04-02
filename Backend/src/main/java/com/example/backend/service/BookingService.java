package com.example.backend.service;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingFilter;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.dtos.Booking.EditBookingRequest;

import java.util.List;

public interface BookingService {
    BookingResponse getBooking(String id);

    BookingResponse addBooking(AddBookingRequest addBookingRequest);

    BookingResponse updateBooking(EditBookingRequest editBookingRequest , String bookingId);

    BookingResponse deleteBooking(String id);

    List<BookingResponse> getMyBookings();

    List<BookingResponse> getSearchMyBookings(BookingFilter filter);

    List<BookingResponse> getBookingForSpace(String spaceId, BookingFilter filter);
}
