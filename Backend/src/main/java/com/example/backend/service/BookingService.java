package com.example.backend.service;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingFilter;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.dtos.Booking.EditBookingRequest;
import com.example.backend.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    BookingResponse getBooking(String id);

    BookingResponse addBooking(AddBookingRequest addBookingRequest);

    BookingResponse updateBooking(EditBookingRequest editBookingRequest , String bookingId) throws AccessDeniedException;

    BookingResponse deleteBooking(String id) throws AccessDeniedException;

    Page<BookingResponse> getMyBookings(Pageable pageable);

    Page<BookingResponse> getSearchMyBookings(Optional<BookingFilter> filter , Pageable pageable);

    Page<BookingResponse> getBookingForSpace(String spaceId, Optional<BookingFilter> filter , Pageable pageable);

    Page<BookingResponse> getSearchAllBookings(Optional<BookingFilter> filter, Pageable pageable);

    BookingResponse updateBookingStatus(Status status, String id) throws AccessDeniedException;
}
