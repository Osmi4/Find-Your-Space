//package com.example.backend.service.impl;
//
//import com.example.backend.dtos.Payment.UpdatePaymentRequest;
//import com.example.backend.dtos.Rating.AddRatingRequest;
//import com.example.backend.dtos.Rating.RatingFilter;
//import com.example.backend.dtos.Rating.RatingResponse;
//import com.example.backend.service.RatingService;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class RatingServiceImplTest {
//
//    @Test
//    void testAddRating() {
//        // Mock dependencies
//        RatingService ratingService = mock(RatingService.class);
//        AddRatingRequest addRatingRequest = new AddRatingRequest();
//        addRatingRequest.setScore(4.5);
//        addRatingRequest.setComment("Good experience");
//        addRatingRequest.setSpaceId("123");
//
//        RatingResponse expectedResponse = new RatingResponse();
//        expectedResponse.setRatingId("123");
//        expectedResponse.setScore(addRatingRequest.getScore());
//        expectedResponse.setComment(addRatingRequest.getComment());
//        expectedResponse.setSpaceId(addRatingRequest.getSpaceId());
//
//        // Test
//        RatingResponse actualResponse = ratingService.addRating(addRatingRequest);
//
//        // Assertion
//        assertNotNull(actualResponse);
//        assertEquals(expectedResponse.getRatingId(), actualResponse.getRatingId());
//        assertEquals(expectedResponse.getScore(), actualResponse.getScore());
//        assertEquals(expectedResponse.getComment(), actualResponse.getComment());
//        assertEquals(expectedResponse.getSpaceId(), actualResponse.getSpaceId());
//    }
//
//    @Test
//    void testGetRating() {
//        // Mock dependencies
//        RatingService ratingService = mock(RatingService.class);
//        RatingResponse expectedResponse = new RatingResponse();
//        expectedResponse.setRatingId("123");
//
//        // Test
//        RatingResponse actualResponse = ratingService.getRating("123");
//
//        // Assertion
//        assertNotNull(actualResponse);
//        assertEquals(expectedResponse.getRatingId(), actualResponse.getRatingId());
//    }
//
//    @Test
//    void testGetRatings() {
//        // Mock dependencies
//        RatingService ratingService = mock(RatingService.class);
//        RatingFilter ratingFilter = new RatingFilter();
//        List<RatingResponse> expectedResponse = new ArrayList<>();
//
//        // Test
//        List<RatingResponse> actualResponse = ratingService.getRatingsByFilters(ratingFilter);
//
//        // Assertion
//        assertNotNull(actualResponse);
//        assertEquals(expectedResponse.size(), actualResponse.size());
//    }
//}
