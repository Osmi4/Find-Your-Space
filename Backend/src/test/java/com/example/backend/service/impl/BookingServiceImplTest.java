package com.example.backend.service.impl;

import com.example.backend.auth.AuthenticationResponse;
import com.example.backend.auth.AuthenticationService;
import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.dtos.Booking.AddBookingRequest;
import com.example.backend.dtos.Booking.BookingFilter;
import com.example.backend.dtos.Booking.BookingResponse;
import com.example.backend.dtos.Booking.EditBookingRequest;
import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import com.example.backend.enums.SpaceType;
import com.example.backend.enums.Status;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.mapper.ObjectMapper;
import com.example.backend.repository.SpaceRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.BookingService;
import com.example.backend.service.SpaceService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BookingServiceImplTest {
    @Autowired
    BookingService bookingService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private SpaceRepository spaceRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private AddBookingRequest addBookingRequest;
    private RegisterDto registerDto;
    private AddSpaceRequest addSpaceRequest;
    @BeforeEach
    public void setUp() {
        registerDto = new RegisterDto();
        registerDto.setEmail("test@ggmail.com");
        registerDto.setFirstName("John");
        registerDto.setLastName("Doe");
        registerDto.setPassword("password");
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto);
        User user = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        addSpaceRequest = new AddSpaceRequest();
        addSpaceRequest.setSpaceName("Space1");
        addSpaceRequest.setSpaceLocation("Location1");
        addSpaceRequest.setSpaceSize(100);
        addSpaceRequest.setSpacePrice(1000);
        addSpaceRequest.setSpaceDescription("Space1 description");
        addSpaceRequest.setSpaceType(SpaceType.OFFICE);

        addBookingRequest = new AddBookingRequest();

    }
    @Test
    void addBooking_Success(){
        SpaceResponse spaceAdded = spaceService.addSpace(addSpaceRequest);

        //new user that will book the space
        RegisterDto registerDto2 = new RegisterDto();
        registerDto2.setEmail("test2@ggmail.com");
        registerDto2.setFirstName("John");
        registerDto2.setLastName("Doe");
        registerDto2.setPassword("password");
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto2);
        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Setting up dates
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime(); // Gets the current date and time
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Adds one day to the current date
        Date endDate = calendar.getTime(); // End date is one day after the start date

        addBookingRequest.setSpaceId(spaceAdded.getSpaceId());
        addBookingRequest.setStartDateTime(startDate);
        addBookingRequest.setEndDateTime(endDate);
        addBookingRequest.setDescription("Description");
        BookingResponse bookingResponse = bookingService.addBooking(addBookingRequest);

        assertNotNull(bookingResponse);
        assertEquals(addBookingRequest.getSpaceId(), bookingResponse.getSpaceId());
        assertEquals(registerDto2.getEmail(), bookingResponse.getClient().getEmail());
        assertEquals(registerDto.getEmail(), bookingResponse.getOwner().getEmail());
    }


    @Test
    void getBooking_Success() {
        SpaceResponse spaceAdded = spaceService.addSpace(addSpaceRequest);

        //add booking
        RegisterDto registerDto2 = new RegisterDto();
        registerDto2.setEmail("test2@ggmail.com");
        registerDto2.setFirstName("John");
        registerDto2.setLastName("Doe");
        registerDto2.setPassword("password");
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto2);
        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Setting up dates
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime(); // Gets the current date and time
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Adds one day to the current date
        Date endDate = calendar.getTime(); // End date is one day after the start date

        addBookingRequest.setSpaceId(spaceAdded.getSpaceId());
        addBookingRequest.setStartDateTime(startDate);
        addBookingRequest.setEndDateTime(endDate);
        addBookingRequest.setDescription("Description");
        BookingResponse bookingResponse = bookingService.addBooking(addBookingRequest);

        BookingResponse bookingResponse2 = bookingService.getBooking(bookingResponse.getBookingId());
        assertNotNull(bookingResponse2);
        assertEquals(bookingResponse.getBookingId(), bookingResponse2.getBookingId());
        assertEquals(bookingResponse.getSpaceId(), bookingResponse2.getSpaceId());
        assertEquals(bookingResponse.getClient().getEmail(), bookingResponse2.getClient().getEmail());
        assertEquals(bookingResponse.getOwner().getEmail(), bookingResponse2.getOwner().getEmail());
    }

    @Test
    void addBooking_InvalidTimeRange() {
        SpaceResponse spaceAdded = spaceService.addSpace(addSpaceRequest);
        addBookingRequest.setSpaceId(spaceAdded.getSpaceId());
        Date startDate = new Date(System.currentTimeMillis() + 86400000); // Tomorrow
        Date endDate = new Date(); // Today, before startDate
        addBookingRequest.setStartDateTime(startDate);
        addBookingRequest.setEndDateTime(endDate);

        // Expect an IllegalArgumentException or similar
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.addBooking(addBookingRequest);
        });
        //assertTrue(exception.getMessage().contains("End date must be after start date"));
    }
    

    @Test
    void updateBooking_Success() {
        SpaceResponse spaceAdded = spaceService.addSpace(addSpaceRequest);
        //add booking
        RegisterDto registerDto2 = new RegisterDto();
        registerDto2.setEmail("test2@ggmail.com");
        registerDto2.setFirstName("John");
        registerDto2.setLastName("Doe");
        registerDto2.setPassword("password");
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto2);
        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Setting up dates
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime(); // Gets the current date and time
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Adds one day to the current date
        Date endDate = calendar.getTime(); // End date is one day after the start date

        addBookingRequest.setSpaceId(spaceAdded.getSpaceId());
        addBookingRequest.setStartDateTime(startDate);
        addBookingRequest.setEndDateTime(endDate);
        addBookingRequest.setDescription("Description");
        BookingResponse bookingResponse = bookingService.addBooking(addBookingRequest);

        calendar.add(Calendar.DAY_OF_MONTH, 1); // Adds one day to the current date
        endDate = calendar.getTime();
        EditBookingRequest editBookingRequest = EditBookingRequest.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();

        BookingResponse bookingResponse2 = bookingService.updateBooking(editBookingRequest, bookingResponse.getBookingId());
        assertNotNull(bookingResponse2);
        assertEquals(bookingResponse.getBookingId(), bookingResponse2.getBookingId());
        assertEquals(bookingResponse.getSpaceId(), bookingResponse2.getSpaceId());
        assertEquals(bookingResponse.getClient().getEmail(), bookingResponse2.getClient().getEmail());
        assertEquals(bookingResponse.getOwner().getEmail(), bookingResponse2.getOwner().getEmail());
    }

    @Test
    void deleteBooking_Success() {
        SpaceResponse spaceAdded = spaceService.addSpace(addSpaceRequest);
        //add booking
        RegisterDto registerDto2 = new RegisterDto();
        registerDto2.setEmail("test2@ggmail.com");
        registerDto2.setFirstName("John");
        registerDto2.setLastName("Doe");
        registerDto2.setPassword("password");
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto2);
        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Setting up dates
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime(); // Gets the current date and time
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Adds one day to the current date
        Date endDate = calendar.getTime(); // End date is one day after the start date

        addBookingRequest.setSpaceId(spaceAdded.getSpaceId());
        addBookingRequest.setStartDateTime(startDate);
        addBookingRequest.setEndDateTime(endDate);
        addBookingRequest.setDescription("Description");
        BookingResponse bookingResponse = bookingService.addBooking(addBookingRequest);

        BookingResponse bookingResponse2 = bookingService.deleteBooking(bookingResponse.getBookingId());
        assertNotNull(bookingResponse2);
        assertEquals(bookingResponse.getBookingId(), bookingResponse2.getBookingId());
    }

    @Test
    void getMyBookings_Success() {
        SpaceResponse spaceAdded = spaceService.addSpace(addSpaceRequest);
        RegisterDto registerDto2 = new RegisterDto();
        registerDto2.setEmail("test2@ggmail.com");
        registerDto2.setFirstName("John");
        registerDto2.setLastName("Doe");
        registerDto2.setPassword("password");
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto2);
        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Setting up dates
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime(); // Gets the current date and time
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Adds one day to the current date
        Date endDate = calendar.getTime(); // End date is one day after the start date

        addBookingRequest.setSpaceId(spaceAdded.getSpaceId());
        addBookingRequest.setStartDateTime(startDate);
        addBookingRequest.setEndDateTime(endDate);
        addBookingRequest.setDescription("Description");
        BookingResponse bookingResponse = bookingService.addBooking(addBookingRequest);

        User user2 = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        authToken = new UsernamePasswordAuthenticationToken(user2, null, user2.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        Pageable pageable = PageRequest.of(0, 10);
        Page<BookingResponse> results = bookingService.getMyBookings(pageable);
        assertNotNull(results);
        assertEquals(1, results.getTotalElements());
    }

    @Test
    void getSearchMyBookings_Success() {
        SpaceResponse spaceAdded = spaceService.addSpace(addSpaceRequest);
        RegisterDto registerDto2 = new RegisterDto();
        registerDto2.setEmail("test2@ggmail.com");
        registerDto2.setFirstName("John");
        registerDto2.setLastName("Doe");
        registerDto2.setPassword("password");
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto2);
        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Setting up dates
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime(); // Gets the current date and time
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Adds one day to the current date
        Date endDate = calendar.getTime(); // End date is one day after the start date

        addBookingRequest.setSpaceId(spaceAdded.getSpaceId());
        addBookingRequest.setStartDateTime(startDate);
        addBookingRequest.setEndDateTime(endDate);
        addBookingRequest.setDescription("Description");
        BookingResponse bookingResponse = bookingService.addBooking(addBookingRequest);

        User user2 = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        authToken = new UsernamePasswordAuthenticationToken(user2, null, user2.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        Pageable pageable = PageRequest.of(0, 10);

        BookingFilter bookingFilter = BookingFilter.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();

        Page<BookingResponse> results = bookingService.getSearchMyBookings(Optional.ofNullable(bookingFilter), pageable);
        assertNotNull(results);
        assertEquals(1, results.getTotalElements());
    }

    @Test
    void getBookingForSpace_Success() {
        SpaceResponse spaceAdded = spaceService.addSpace(addSpaceRequest);
        RegisterDto registerDto2 = new RegisterDto();
        registerDto2.setEmail("test2@ggmail.com");
        registerDto2.setFirstName("John");
        registerDto2.setLastName("Doe");
        registerDto2.setPassword("password");
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto2);
        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Setting up dates
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime(); // Gets the current date and time
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Adds one day to the current date
        Date endDate = calendar.getTime(); // End date is one day after the start date

        addBookingRequest.setSpaceId(spaceAdded.getSpaceId());
        addBookingRequest.setStartDateTime(startDate);
        addBookingRequest.setEndDateTime(endDate);
        addBookingRequest.setDescription("Description");
        BookingResponse bookingResponse = bookingService.addBooking(addBookingRequest);

        User user2 = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
        authToken = new UsernamePasswordAuthenticationToken(user2, null, user2.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        Pageable pageable = PageRequest.of(0, 10);
        BookingFilter bookingFilter = BookingFilter.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();

        Page<BookingResponse> results = bookingService.getBookingForSpace(spaceAdded.getSpaceId(), Optional.ofNullable(bookingFilter),pageable);

        assertNotNull(results);
        assertEquals(1, results.getTotalElements());
    }

    @Test
    void getSearchAllBookings_Success() {
        SpaceResponse spaceAdded = spaceService.addSpace(addSpaceRequest);
        RegisterDto registerDto2 = new RegisterDto();
        registerDto2.setEmail("test2@ggmail.com");
        registerDto2.setFirstName("John");
        registerDto2.setLastName("Doe");
        registerDto2.setPassword("password");
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto2);
        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Setting up dates
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime(); // Gets the current date and time
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Adds one day to the current date
        Date endDate = calendar.getTime(); // End date is one day after the start date

        addBookingRequest.setSpaceId(spaceAdded.getSpaceId());
        addBookingRequest.setStartDateTime(startDate);
        addBookingRequest.setEndDateTime(endDate);
        addBookingRequest.setDescription("Description");
        BookingResponse bookingResponse = bookingService.addBooking(addBookingRequest);

        Pageable pageable = PageRequest.of(0, 10);
        BookingFilter bookingFilter = BookingFilter.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();

        Page<BookingResponse> results = bookingService.getSearchAllBookings(Optional.ofNullable(bookingFilter), pageable);
        assertNotNull(results);
        assertEquals(1, results.getTotalElements());
    }
    @Test
    void UpdateBookingStatus_Success() {
        SpaceResponse spaceAdded = spaceService.addSpace(addSpaceRequest);
        RegisterDto registerDto2 = new RegisterDto();
        registerDto2.setEmail("test2@ggmail.com");
        registerDto2.setFirstName("John");
        registerDto2.setLastName("Doe");
        registerDto2.setPassword("password");
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto2);
        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Setting up dates
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime(); // Gets the current date and time
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Adds one day to the current date
        Date endDate = calendar.getTime(); // End date is one day after the start date

        addBookingRequest.setSpaceId(spaceAdded.getSpaceId());
        addBookingRequest.setStartDateTime(startDate);
        addBookingRequest.setEndDateTime(endDate);
        addBookingRequest.setDescription("Description");
        BookingResponse bookingResponse = bookingService.addBooking(addBookingRequest);

        User user2 = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
        authToken = new UsernamePasswordAuthenticationToken(user2, null, user2.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        BookingResponse bookingResponse2 = bookingService.updateBookingStatus(Status.CANCELLED, bookingResponse.getBookingId());
        assertNotNull(bookingResponse2);
        assertEquals(bookingResponse.getBookingId(), bookingResponse2.getBookingId());
        assertEquals(Status.CANCELLED, bookingResponse2.getStatus());
    }
}
