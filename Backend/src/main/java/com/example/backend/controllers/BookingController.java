package com.example.backend.controllers;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingFilter;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.dtos.Booking.EditBookingRequest;
import com.example.backend.exception.ErrorCreator;
import com.example.backend.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //scal te dwie mteody zeby filter byl opcjonalny
    @GetMapping("/my-Bookings")
    //dodac filter opcjonalnie
    public ResponseEntity<List<BookingResponse>> getMyBookings() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }
    //wywalic searche z patha jezeli sie da
    @GetMapping("/search/my-Bookings")
    public ResponseEntity<List<BookingResponse>> searchMyBookings(@RequestBody BookingFilter filter){
        return ResponseEntity.ok(bookingService.getSearchMyBookings(filter));
    }
    ///sorting + pomyslec gdzie to
    @GetMapping("/search/to-my-space/{SpaceId}")
    public ResponseEntity<List<BookingResponse>> searchToMySpace(@PathVariable String SpaceId,@RequestBody BookingFilter filter){
        return ResponseEntity.ok(bookingService.getBookingForSpace(SpaceId , filter));
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBooking(@PathVariable String id){
        return ResponseEntity.ok(bookingService.getBooking(id));
    }
    @PostMapping("/add")
    public ResponseEntity<BookingResponse> addBooking(@RequestBody AddBookingRequest addBookingRequest){
        return ResponseEntity.ok(bookingService.addBooking(addBookingRequest));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable String id , @RequestBody EditBookingRequest editBookingRequest){
        return ResponseEntity.ok(bookingService.updateBooking(editBookingRequest , id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BookingResponse> deleteBooking(@PathVariable String id){
        return ResponseEntity.ok(bookingService.deleteBooking(id));
    }
    


}
