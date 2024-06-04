package com.example.backend.controllers;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingFilter;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.dtos.Booking.EditBookingRequest;
import com.example.backend.entity.Booking;
import com.example.backend.enums.Status;
import com.example.backend.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/all")
    public ResponseEntity<Page<BookingResponse>> searchAllBookings(@Valid @RequestBody Optional<BookingFilter> filter, @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(bookingService.getSearchAllBookings(filter, pageable));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<BookingResponse>> searchAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.getAllBookings(pageable));
    }

    @GetMapping("/my-Bookings")
    public ResponseEntity<Page<BookingResponse>> searchMyBookings(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.getMyBookings(pageable));
    }

    @PostMapping("/spaces-owner/{SpaceId}")
    public ResponseEntity<Page<BookingResponse>> searchToMySpace(@PathVariable String SpaceId, @Valid @RequestBody Optional<BookingFilter> filter, @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.getBookingForSpace(SpaceId, filter, pageable));
    }

    @GetMapping("/{spaceId}/bookings")
    public ResponseEntity<Page<BookingResponse>> getBookings(@PathVariable String spaceId, @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(bookingService.getBookingsForSpace(spaceId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable String id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @PostMapping("")
    public ResponseEntity<BookingResponse> addBooking(@RequestBody AddBookingRequest addBookingRequest) {
        return ResponseEntity.ok(bookingService.addBooking(addBookingRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable String id, @RequestBody EditBookingRequest editBookingRequest) throws AccessDeniedException {
        return ResponseEntity.ok(bookingService.updateBooking(editBookingRequest, id));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<BookingResponse> updateStatus(@PathVariable String id, @RequestBody Status status) throws AccessDeniedException {
        return ResponseEntity.ok(bookingService.updateBookingStatus(status, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingResponse> deleteBooking(@PathVariable String id) throws AccessDeniedException {
        return ResponseEntity.ok(bookingService.deleteBooking(id));
    }

}
