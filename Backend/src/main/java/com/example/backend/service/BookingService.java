package com.example.backend.service;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingFilter;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.dtos.Booking.EditBookingRequest;
import com.example.backend.enums.Status;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    BookingResponse getBooking(String id);

    BookingResponse addBooking(AddBookingRequest addBookingRequest);

    BookingResponse updateBooking(EditBookingRequest editBookingRequest , String bookingId);

    BookingResponse deleteBooking(String id);

    List<BookingResponse> getMyBookings();

    List<BookingResponse> getSearchMyBookings(Optional<BookingFilter> filter);

    List<BookingResponse> getBookingForSpace(String spaceId, Optional<BookingFilter> filter);

    List<BookingResponse> getSearchAllBookings(Optional<BookingFilter> filter);

    BookingResponse updateBookingStatus(Status status, String id);
}
