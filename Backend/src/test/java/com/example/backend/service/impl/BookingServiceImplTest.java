//package com.example.backend.service.impl;
//
//import com.example.backend.dtos.Booking.AddBookingRequest;
//import com.example.backend.dtos.Booking.BookingFilter;
//import com.example.backend.dtos.Booking.EditBookingRequest;
//import com.example.backend.entity.Space;
//import com.example.backend.entity.User;
//import com.example.backend.enums.Role;
//import com.example.backend.enums.SpaceType;
//import com.example.backend.enums.Status;
//import com.example.backend.repository.SpaceRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import com.example.backend.repository.BookingRepository;
//import com.example.backend.service.impl.BookingServiceImpl;
//import com.example.backend.entity.Booking;
//import com.example.backend.dtos.Booking.BookingResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class BookingServiceImplTest {
//
//    @Mock
//    private BookingRepository bookingRepository;
//    @Mock
//    private SpaceRepository spaceRepository;
//
//
//    @InjectMocks
//    private BookingServiceImpl bookingService;
//
//    private Booking booking;
//    private User user;
//    private User userC;
//    private Space space;
//    private Space space2;
//    private Booking booking2;
//    private List<Booking> bookings;
//
//    @BeforeEach
//    public void setUp() {
//        user = User.builder()
//                .userId("1")
//                .role(Role.USER)
//                .password("password")
//                .email("test@gmail.com")
//                .firstName("John")
//                .lastName("Doe").build();
//        userC = User.builder()
//                .userId("2")
//                .role(Role.USER)
//                .password("password")
//                .email("testc@gmail.com")
//                .firstName("John")
//                .lastName("Doe").build();
//        space = Space.builder()
//                .spaceId("1")
//                .spaceName("Test Space")
//                .spaceLocation("Test Location")
//                .spaceSize(100)
//                .spacePrice(1500)
//                .spaceImage("image.jpg")
//                .spaceDescription("Test Description")
//                .spaceType(SpaceType.OFFICE)
//                .dateAdded(new Date())
//                .dateUpdated(new Date())
//                .owner(user)
//                .build();
//        space2 = Space.builder()
//                .spaceId("2")
//                .spaceName("Test Space")
//                .spaceLocation("Test Location")
//                .spaceSize(100)
//                .spacePrice(1500)
//                .spaceImage("image.jpg")
//                .spaceDescription("Test Description")
//                .spaceType(SpaceType.OFFICE)
//                .dateAdded(new Date())
//                .dateUpdated(new Date())
//                .owner(userC)
//                .build();
//        booking = Booking.builder()
//                .bookingId("1")
//                .startDateTime(new Date())
//                .endDateTime(new Date())
//                .cost(100.00)
//                .dateAdded(new Date())
//                .dateUpdated(new Date())
//                .status(Status.INQUIRY)
//                .client(userC)
//                .space(space)
//                .build();
//        booking2 = Booking.builder()
//                .bookingId("2")
//                .startDateTime(new Date())
//                .endDateTime(new Date())
//                .cost(100.00)
//                .dateAdded(new Date())
//                .dateUpdated(new Date())
//                .status(Status.INQUIRY)
//                .client(user)
//                .space(space)
//                .build();
//        bookings = new ArrayList<>(List.of(booking, booking2));
//    }
//    @BeforeEach
//    public void setUpSecurityContext() {
//        User mockUser = User.builder()
//                .userId("1")
//                .role(Role.USER)
//                .password("password")
//                .email("test@gmail.com")
//                .firstName("John")
//                .lastName("Doe").build();
//        Authentication auth = new UsernamePasswordAuthenticationToken(mockUser, null);
//        SecurityContext securityContext = mock(SecurityContext.class);
//        when(securityContext.getAuthentication()).thenReturn(auth);
//        Mockito.lenient().when(securityContext.getAuthentication()).thenReturn(auth);
//        SecurityContextHolder.setContext(securityContext);
//    }
//
//    @Test
//    public void testGetBooking_Found() {
//        when(bookingRepository.findByBookingId("1")).thenReturn(Optional.of(booking));
//        BookingResponse result = bookingService.getBooking("1");
//        assertNotNull(result);
//        assertEquals("1", result.getBookingId());
//        assertEquals(booking.getCost(), result.getCost());
//        verify(bookingRepository).findByBookingId("1");
//    }
//    @Test
//    public void testAddBooking_SpaceNotAvailable() {
//        AddBookingRequest request = new AddBookingRequest();
//        request.setSpaceId("space1");
//        request.setStartDate(new Date(System.currentTimeMillis() + 100000));
//        request.setEndDate(new Date(System.currentTimeMillis() + 200000));
//        when(bookingRepository.findBySpace_SpaceId("space1")).thenReturn(List.of(booking));
//        assertThrows(RuntimeException.class, () -> bookingService.addBooking(request));
//    }
//    @Test
//    public void testAddBooking_Success() {
//        AddBookingRequest request = new AddBookingRequest();
//        request.setSpaceId("2");
//        request.setStartDate(new Date(System.currentTimeMillis() + 100000));
//        request.setEndDate(new Date(System.currentTimeMillis() + 200000));
//        when(spaceRepository.findBySpaceId("2")).thenReturn(Optional.of(space2));
//        when(bookingRepository.findBySpace_SpaceId("2")).thenReturn(List.of());
//        when(bookingRepository.save(any())).thenReturn(booking);
//
//        BookingResponse result = bookingService.addBooking(request);
//
//        assertNotNull(result);
//        verify(bookingRepository).save(any());
//    }
//    @Test
//    public void testUpdateBooking_Success() {
//        EditBookingRequest request = EditBookingRequest.builder()
//                .startDate(new Date(new Date().getTime() + 7200000))
//                .endDate(new Date(new Date().getTime() + 10800000))
//                .build();
//        when(bookingRepository.findByBookingId("2")).thenReturn(Optional.of(booking2));
//        when(bookingRepository.findByBookingId("2")).thenReturn(Optional.of(booking2));
//        when(bookingRepository.findBySpace_SpaceId("1")).thenReturn(new ArrayList<>(List.of(booking2)));
//        when(bookingRepository.save(any())).thenReturn(booking2);
//        BookingResponse response = bookingService.updateBooking(request, "2");
//        assertNotNull(response);
//    }
//    @Test
//    public void testDeleteBooking_Success() {
//        when(bookingRepository.findByBookingId("2")).thenReturn(Optional.of(booking2) , Optional.empty());
//        when(bookingRepository.deleteByBookingId("2")).thenReturn(1);
//        BookingResponse response = bookingService.deleteBooking("2");
//        assertNotNull(response);
//        verify(bookingRepository).deleteByBookingId("2");
//    }
//    @Test
//    public void testGetMyBookings_Success() {
//        when(bookingRepository.findByClient_UserId(user.getUserId())).thenReturn(bookings);
//        List<BookingResponse> results = bookingService.getMyBookings();
//        assertNotNull(results);
//        assertEquals(2, results.size());
//    }
//    @Test
//    public void testGetSearchMyBookings_WithFilter() {
//        BookingFilter filter = BookingFilter.builder()
//                .startDate(new Date())
//                .endDate(new Date())
//                .status(Status.INQUIRY)
//                .build();
//        when(bookingRepository.filterQuery(new Date(), new Date(), "1", null, null)).thenReturn(bookings);
//        List<BookingResponse> results = bookingService.getSearchMyBookings(Optional.of(filter));
//
//        assertNotNull(results);
//        assertFalse(results.isEmpty());
//        assertEquals(2, results.size());
//    }
//    @Test
//    public void testUpdateBookingStatus_SuccessFromInquiryToAccepted() {
//        when(bookingRepository.findByBookingId("1")).thenReturn(Optional.of(booking));
//        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        BookingResponse result = bookingService.updateBookingStatus(Status.ACCEPTED, "1");
//
//        assertNotNull(result);
//        assertEquals(Status.ACCEPTED, result.getStatus());
//        verify(bookingRepository).save(booking);
//    }
//
//
//
//}
