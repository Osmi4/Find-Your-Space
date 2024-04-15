package com.example.backend.controllers;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingFilter;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.dtos.Booking.EditBookingRequest;
import com.example.backend.enums.Status;
import com.example.backend.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<BookingResponse>> searchAllBookings(@Valid @RequestBody Optional<BookingFilter> filter){
        return ResponseEntity.ok(bookingService.getSearchAllBookings(filter));
    }
    @GetMapping("/my-Bookings")
    public ResponseEntity<List<BookingResponse>> searchMyBookings(@Valid @RequestBody Optional<BookingFilter> filter){
        return ResponseEntity.ok(bookingService.getSearchMyBookings(filter));
    }
    @GetMapping("/spaces-owner/{SpaceId}")
    public ResponseEntity<List<BookingResponse>> searchToMySpace(@PathVariable String SpaceId,@Valid @RequestBody Optional<BookingFilter> filter){
        return ResponseEntity.ok(bookingService.getBookingForSpace(SpaceId , filter));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable String id){
        return ResponseEntity.ok(bookingService.getBooking(id));
    }
    @PostMapping("")
    public ResponseEntity<BookingResponse> addBooking(@RequestBody AddBookingRequest addBookingRequest){
        return ResponseEntity.ok(bookingService.addBooking(addBookingRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable String id , @RequestBody EditBookingRequest editBookingRequest){
        return ResponseEntity.ok(bookingService.updateBooking(editBookingRequest , id));
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<BookingResponse> updateStatus(@PathVariable String id , @RequestBody Status status){
        return ResponseEntity.ok(bookingService.updateBookingStatus(status , id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<BookingResponse> deleteBooking(@PathVariable String id){
        return ResponseEntity.ok(bookingService.deleteBooking(id));
    }




}
