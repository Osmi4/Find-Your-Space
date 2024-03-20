package com.example.backend.service.impl;

import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.service.BookingService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceImplTest {

    @Test
    void testGetBooking() {
        // Mock dependencies
        BookingService bookingService = mock(BookingService.class);
        BookingResponse expectedResponse = new BookingResponse();
        expectedResponse.setBookingId("123");

        // Test
        BookingResponse actualResponse = bookingService.getBooking("123");

        // Assertion
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getBookingId(), actualResponse.getBookingId());
    }

    @Test
    void testAddBooking() {
        // Mock dependencies
        BookingService bookingService = mock(BookingService.class);
        AddBookingRequest addBookingRequest = new AddBookingRequest();
        addBookingRequest.setSpaceId("456");
        addBookingRequest.setStartDate("2024-03-20");
        addBookingRequest.setEndDate("2024-03-22");

        BookingResponse expectedResponse = new BookingResponse();
        expectedResponse.setBookingId("456");
        expectedResponse.setSpaceId("456");
        expectedResponse.setStartDate("2024-03-20");
        expectedResponse.setEndDate("2024-03-22");

        // Test
        BookingResponse actualResponse = bookingService.addBooking(addBookingRequest);

        // Assertion
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getBookingId(), actualResponse.getBookingId());
        assertEquals(expectedResponse.getSpaceId(), actualResponse.getSpaceId());
        assertEquals(expectedResponse.getStartDate(), actualResponse.getStartDate());
        assertEquals(expectedResponse.getEndDate(), actualResponse.getEndDate());
    }

    @Test
    void testUpdateBooking() {
        // Mock dependencies
        BookingService bookingService = mock(BookingService.class);
        AddBookingRequest addBookingRequest = new AddBookingRequest();
        addBookingRequest.setSpaceId("789");
        addBookingRequest.setStartDate("2024-03-25");
        addBookingRequest.setEndDate("2024-03-27");

        BookingResponse expectedResponse = new BookingResponse();
        expectedResponse.setBookingId("789");
        expectedResponse.setSpaceId("789");
        expectedResponse.setStartDate("2024-03-25");
        expectedResponse.setEndDate("2024-03-27");

        // Test
        BookingResponse actualResponse = bookingService.updateBooking(addBookingRequest);

        // Assertion
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getBookingId(), actualResponse.getBookingId());
        assertEquals(expectedResponse.getSpaceId(), actualResponse.getSpaceId());
        assertEquals(expectedResponse.getStartDate(), actualResponse.getStartDate());
        assertEquals(expectedResponse.getEndDate(), actualResponse.getEndDate());
    }

    @Test
    void testDeleteBooking() {
        // Mock dependencies
        BookingService bookingService = mock(BookingService.class);
        BookingResponse expectedResponse = new BookingResponse();
        expectedResponse.setBookingId("987");

        // Test
        BookingResponse actualResponse = bookingService.deleteBooking("987");

        // Assertion
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getBookingId(), actualResponse.getBookingId());
    }
}
