package com.example.backend.service.impl;

import com.example.backend.auth.AuthenticationResponse;
import com.example.backend.auth.AuthenticationService;
import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.dtos.Rating.AddRatingRequest;
import com.example.backend.dtos.Rating.RatingFilter;
import com.example.backend.dtos.Rating.RatingResponse;
import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.entity.User;
import com.example.backend.enums.SpaceType;
import com.example.backend.repository.SpaceRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.RatingService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RatingServiceImplTest{
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private RatingService ratingService;
    AddSpaceRequest addSpaceRequest;
    @BeforeEach
    public void setUp() {
        RegisterDto registerDto = new RegisterDto();
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
        addSpaceRequest.setSpaceImage("Image1");
        addSpaceRequest.setSpaceDescription("Space1 description");
        addSpaceRequest.setSpaceType(SpaceType.OFFICE);
    }
    @Test
    void addRatingTest_Success() {
        SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);

        // Given
        AddRatingRequest addRatingRequest = new AddRatingRequest();
        addRatingRequest.setScore(5);
        addRatingRequest.setComment("Great space");
        addRatingRequest.setSpaceId(spaceResponse.getSpaceId());
        // When
        RatingResponse ratingResponse = ratingService.addRating(addRatingRequest);
        // Then
        assertNotNull(ratingResponse);
        assertEquals(5, ratingResponse.getScore());
        assertEquals("Great space", ratingResponse.getComment());
        assertEquals(spaceResponse.getSpaceId(), ratingResponse.getSpaceId());
    }

    @Test
    void getRatingTest_Success() {
        // Given
        SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);
        AddRatingRequest addRatingRequest = new AddRatingRequest();
        addRatingRequest.setScore(5);
        addRatingRequest.setComment("Great space");
        addRatingRequest.setSpaceId(spaceResponse.getSpaceId());
        RatingResponse ratingResponse = ratingService.addRating(addRatingRequest);
        // When
        RatingResponse ratingResponse1 = ratingService.getRating(ratingResponse.getRatingId());
        // Then
        assertNotNull(ratingResponse1);
        assertEquals(ratingResponse.getRatingId(), ratingResponse1.getRatingId());
        assertEquals(ratingResponse.getScore(), ratingResponse1.getScore());
        assertEquals(ratingResponse.getComment(), ratingResponse1.getComment());
        assertEquals(ratingResponse.getSpaceId(), ratingResponse1.getSpaceId());
    }

    @Test
    void deleteRatingTest_Success() {
        // Given
        SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);
        AddRatingRequest addRatingRequest = new AddRatingRequest();
        addRatingRequest.setScore(5);
        addRatingRequest.setComment("Great space");
        addRatingRequest.setSpaceId(spaceResponse.getSpaceId());
        RatingResponse ratingResponse = ratingService.addRating(addRatingRequest);
        // When
        ratingService.deleteRating(ratingResponse.getRatingId());
        // Then
    }

    @Test
    void getRatingsByFiltersTest_Success() {
        // Given
        SpaceResponse spaceResponse = spaceService.addSpace(addSpaceRequest);
        AddRatingRequest addRatingRequest = new AddRatingRequest();
        addRatingRequest.setScore(5);
        addRatingRequest.setComment("Great space");
        addRatingRequest.setSpaceId(spaceResponse.getSpaceId());
        RatingResponse ratingResponse = ratingService.addRating(addRatingRequest);

        Pageable pageable = PageRequest.of(0, 10);
        RatingFilter ratingFilter = new RatingFilter();
        ratingFilter.setSpaceId(spaceResponse.getSpaceId());
        Page<RatingResponse> ratingResponses = ratingService.getRatingsByFilters(ratingFilter, pageable);
        // Then
        assertNotNull(ratingResponses);
        assertEquals(1, ratingResponses.getTotalElements());
    }

}

