package com.example.backend.service.impl;

import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import com.example.backend.enums.Role;
import com.example.backend.enums.SpaceType;
import com.example.backend.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.backend.repository.BookingRepository;
import com.example.backend.service.impl.BookingServiceImpl;
import com.example.backend.entity.Booking;
import com.example.backend.dtos.Booking.BookingResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Booking booking;
    private User user;
    private User userC;
    private Space space;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .userId("1")
                .role(Role.USER)
                .password("password")
                .email("test@gmail.com")
                .firstName("John")
                .lastName("Doe").build();
        userC = User.builder()
                .userId("2")
                .role(Role.USER)
                .password("password")
                .email("testc@gmail.com")
                .firstName("John")
                .lastName("Doe").build();
        space = Space.builder()
                .spaceId("1")
                .spaceName("Test Space")
                .spaceLocation("Test Location")
                .spaceSize(100)
                .spacePrice(1500)
                .spaceImage("image.jpg")
                .spaceDescription("Test Description")
                .spaceType(SpaceType.OFFICE)
                .dateAdded(new Date())
                .dateUpdated(new Date())
                .owner(user)
                .build();
        booking = Booking.builder()
                .bookingId("1")
                .startDateTime(new Date())
                .endDateTime(new Date())
                .cost(100.00)
                .dateAdded(new Date())
                .dateUpdated(new Date())
                .status(Status.INQUIRY)
                .client(userC)
                .space(space)
                .build();
    }
    @BeforeEach
    public void setUpSecurityContext() {
        User mockUser = User.builder()
                .userId("1")
                .role(Role.USER)
                .password("password")
                .email("test@gmail.com")
                .firstName("John")
                .lastName("Doe").build();
        Authentication auth = new UsernamePasswordAuthenticationToken(mockUser, null);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        Mockito.lenient().when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetBooking_Found() {
        when(bookingRepository.findByBookingId("1")).thenReturn(Optional.of(booking));
        BookingResponse result = bookingService.getBooking("1");
        assertNotNull(result);
        assertEquals("1", result.getBookingId());
        assertEquals(booking.getCost(), result.getCost());
        verify(bookingRepository).findByBookingId("1");
    }
}
