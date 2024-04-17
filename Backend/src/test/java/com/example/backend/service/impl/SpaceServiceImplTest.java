package com.example.backend.service.impl;

import com.example.backend.dtos.Space.*;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.Booking;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import com.example.backend.enums.Availibility;
import com.example.backend.enums.Role;
import com.example.backend.enums.SpaceType;
import com.example.backend.repository.BookingRepository;
import com.example.backend.repository.SpaceRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.impl.SpaceServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpaceServiceImplTest{
    @Mock
    private SpaceRepository spaceRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private Authentication authentication;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private SpaceServiceImpl spaceService;

    private AddSpaceRequest addSpaceRequest;
    private Space space;
    private SpaceResponse spaceResponse;
    private Space space1;
    private Space space2;
    private List<Space> spaces;
    private User user;
    private List<Booking> bookings;

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
    @BeforeEach
    public void setUp() {
         user = User.builder()
                .userId("1")
                .role(Role.USER)
                .password("password")
                .email("test@gmail.com")
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

        spaceResponse = SpaceResponse.builder()
                .spaceId("1")
                .spaceName("Test Space")
                .spaceLocation("Test Location")
                .spaceSize(100)
                .spacePrice(1500)
                .spaceImage("image.jpg")
                .owner(UserResponse.builder()
                        .userId("1")
                        .userEmail("test@gmail.com")
                        .firstName("John")
                        .lastName("Doe").build())
                .build();
        space1 = Space.builder()
                .spaceId("1")
                .spaceName("Conference Room")
                .spaceLocation("First Floor")
                .spaceType(SpaceType.OFFICE)
                .availibility(Availibility.AVAILABLE)
                .spaceSize(150)
                .spacePrice(1000)
                .owner(user)
                .build();

        space2 = Space.builder()
                .spaceId("2")
                .spaceName("Meeting Room")
                .spaceLocation("Second Floor")
                .spaceType(SpaceType.EVENT_SPACE)
                .availibility(Availibility.NOT_RELEASED)
                .spaceSize(75)
                .spacePrice(500)
                .owner(user)
                .build();
        Date now = new Date();
        Booking booking1 = new Booking();
        booking1.setBookingId("1");
        booking1.setSpace(space);
        booking1.setStartDateTime(new Date(now.getTime()));
        booking1.setEndDateTime(new Date(now.getTime() + 2 * 60 * 60 * 1000));

        Booking booking2 = new Booking();
        booking2.setBookingId("2");
        booking2.setSpace(space);
        booking2.setStartDateTime(new Date(now.getTime()));
        booking2.setEndDateTime(new Date(now.getTime() + 2 * 60 * 60 * 1000));

        bookings = List.of(booking1, booking2);

        spaces = new ArrayList<>(List.of(space1, space2));
    }

    @Test
    public void testAddSpace_Success() {
        AddSpaceRequest addSpaceRequest = AddSpaceRequest.builder()
                .spaceName("Test Space")
                .spaceLocation("Test Location")
                .spaceSize(100)
                .spacePrice(1500)
                .spaceImage("image.jpg")
                .spaceDescription("Test Description")
                .spaceType(SpaceType.OFFICE)
                .build();
        when(spaceRepository.save(any(Space.class))).thenReturn(space);
        SpaceResponse result = spaceService.addSpace(addSpaceRequest);
        assertNotNull(result);
        Assert.assertEquals(result.getSpaceId(), spaceResponse.getSpaceId());
        Assert.assertEquals(result.getSpaceName(), spaceResponse.getSpaceName());
        Assert.assertEquals(result.getSpaceLocation(), spaceResponse.getSpaceLocation());
        Assert.assertEquals(result.getSpaceSize(), spaceResponse.getSpaceSize() , 0.001);
        Assert.assertEquals(result.getSpacePrice(), spaceResponse.getSpacePrice() , 0.001);
        Assert.assertEquals(result.getSpaceImage(), spaceResponse.getSpaceImage());
        Assert.assertEquals(result.getAvailability() , spaceResponse.getAvailability());
        Assert.assertEquals(result.getOwner().getUserId() , spaceResponse.getOwner().getUserId());
    }
    @Test
    public void testEditSpace_Success() {
        // Setup
        EditSpaceRequest editRequest = EditSpaceRequest.builder()
                .spaceName("New Name")
                .spaceSize(200)
                .spacePrice(2000)
                .spaceLocation("New Location")
                .spaceDescription("New Description")
                .build();
        when(spaceRepository.findBySpaceId("1")).thenReturn(Optional.of(space));
        when(spaceRepository.save(any(Space.class))).thenReturn(space);
        SpaceResponse result = spaceService.editSpace(editRequest, "1");

        assertNotNull(result);
        Assert.assertEquals(result.getSpaceId(), spaceResponse.getSpaceId());
        Assert.assertEquals(result.getSpaceName(), "New Name");
        Assert.assertEquals(result.getSpaceLocation(), "New Location");
        Assert.assertEquals(result.getSpaceSize(), 200 , 0.001);
        Assert.assertEquals(result.getSpacePrice(), 2000 , 0.001);
        verify(spaceRepository).save(space);
    }
    @Test
    public void testDeleteSpace_Success() {
        when(spaceRepository.deleteBySpaceId("1")).thenReturn(1);
        when(spaceRepository.findBySpaceId("1")).thenReturn(Optional.of(space), Optional.empty());
        SpaceResponse result = spaceService.deleteSpace("1");
        assertNotNull(result);
        Assert.assertEquals("1", result.getSpaceId());
        verify(spaceRepository).deleteBySpaceId("1");
        verify(spaceRepository, times(2)).findBySpaceId("1");
    }
    @Test
    public void testSearchSpaces_Success() {
        SpaceFilter filter = SpaceFilter.builder()
                .spaceName("Conference")
                .spaceLocation("First Floor")
                .spaceSizeLowerBound(100)
                .spaceSizeUpperBound(1500)  // Adjusted to match actual method call
                .spacePriceLowerBound(500)
                .spacePriceUpperBound(1500)
                .spaceType(SpaceType.OFFICE)
                .availability(Availibility.AVAILABLE)
                .build();

        when(spaceRepository.findSpacesByPriceRangeAndSizeLimitForOwner(1500.0d, 500.0d, 1500.0d, 100.0d, null)).thenReturn(spaces);
        List<SpaceResponse> results = spaceService.searchSpaces(filter);

        assertNotNull(results);
        Assert.assertEquals(1, results.size()); // Assuming that only space1 matches the filter
        Assert.assertEquals("Conference Room", results.get(0).getSpaceName());

        verify(spaceRepository).findSpacesByPriceRangeAndSizeLimitForOwner(1500.0d, 500.0d, 1500.0d, 100.0d, null);
    }
    @Test
    public void testGetSpace_Success() {
        when(spaceRepository.findBySpaceId("1")).thenReturn(Optional.of(space1));

        SpaceResponse result = spaceService.getSpace("1");

        assertNotNull(result);
        Assert.assertEquals("1", result.getSpaceId());
        Assert.assertEquals("Conference Room", result.getSpaceName());
        Assert.assertEquals("First Floor", result.getSpaceLocation());
        Assert.assertEquals(150, result.getSpaceSize(), 0.001);
        Assert.assertEquals(1000, result.getSpacePrice(), 0.001);
        Assert.assertEquals("1", result.getOwner().getUserId());

        // Verify interaction with the repository
        verify(spaceRepository).findBySpaceId("1");
    }
    @Test
    public void testChangeAvailability_Success() {
        when(spaceRepository.findBySpaceId("1")).thenReturn(Optional.of(space));
        when(spaceRepository.save(space)).thenReturn(space);  // Optional: simulate the save operation

        Availibility newAvailability = Availibility.NOT_RELEASED;
        SpaceResponse result = spaceService.changeAvailability("1", newAvailability);

        assertNotNull(result);
        Assert.assertEquals(newAvailability, result.getAvailability());

        verify(spaceRepository).findBySpaceId("1");
        verify(spaceRepository).save(space);
    }

    @Test
    public void testGetMySpaces() {
        SpaceFilter filter = new SpaceFilter();
        when(spaceRepository.findSpacesByPriceRangeAndSizeLimitForOwner(anyDouble(), anyDouble(), anyDouble(), anyDouble(), eq(user.getUserId())))
                .thenReturn(spaces);

        List<SpaceResponse> results = spaceService.getMySpaces(filter);

        assertNotNull(results);
        Assert.assertEquals(2, results.size()); // Assuming the filter matches both spaces
        Assert.assertTrue(results.stream().anyMatch(r -> r.getSpaceId().equals("1")));
        Assert.assertTrue(results.stream().anyMatch(r -> r.getSpaceId().equals("2")));

        verify(spaceRepository).findSpacesByPriceRangeAndSizeLimitForOwner(anyDouble(), anyDouble(), anyDouble(), anyDouble(), eq(user.getUserId()));
    }
    @Test
    public void testGetAllSpaces() {
        when(spaceRepository.findAll()).thenReturn(spaces);

        List<SpaceResponse> results = spaceService.getAllSpaces();

        assertNotNull(results);
        Assert.assertEquals(2, results.size());
        Assert.assertTrue(results.stream().anyMatch(r -> r.getSpaceId().equals("1")));
        Assert.assertTrue(results.stream().anyMatch(r -> r.getSpaceId().equals("2")));

        verify(spaceRepository).findAll();
    }
    @Test
    public void testGetBookedDates_SpaceExists() {
        when(spaceRepository.findBySpaceId("1")).thenReturn(Optional.of(space));
        when(bookingRepository.findBySpace_SpaceId("1")).thenReturn(bookings);

        SpaceBookedDates result = spaceService.getBookedDates("1");

        assertNotNull(result);
        assertNotNull(result.getBookedDates());
        Assert.assertEquals(2, result.getBookedDates().size());

        verify(spaceRepository).findBySpaceId("1");
        verify(bookingRepository).findBySpace_SpaceId("1");
    }

}