package com.example.backend.service.impl;

import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.service.SpaceService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SpaceServiceImplTest {

    @Test
    void testAddSpace() {
        // Mock dependencies
//        SpaceService spaceService = mock(SpaceService.class);
//        AddSpaceRequest addSpaceRequest = new AddSpaceRequest();
//        addSpaceRequest.setSpaceName("Example Space");
//        addSpaceRequest.setSpaceLocation("Location");
//        addSpaceRequest.setSpaceSize(100.0);
//        addSpaceRequest.setSpacePrice(50.0);
//        addSpaceRequest.setSpaceImage("image.png");
//        //addSpaceRequest.setAvailability("Available");
//
//        SpaceResponse expectedResponse = new SpaceResponse();
//        expectedResponse.setSpaceId("123");
//        expectedResponse.setSpaceName(addSpaceRequest.getSpaceName());
//        expectedResponse.setSpaceLocation(addSpaceRequest.getSpaceLocation());
//        expectedResponse.setSpaceSize(addSpaceRequest.getSpaceSize());
//        expectedResponse.setSpacePrice(addSpaceRequest.getSpacePrice());
//        expectedResponse.setSpaceImage(addSpaceRequest.getSpaceImage());
//        expectedResponse.setAvailability(addSpaceRequest.getAvailability());
//
//        // Test
//        SpaceResponse actualResponse = spaceService.addSpace(addSpaceRequest);
//
//        // Assertion
//        assertNotNull(actualResponse);
//        assertEquals(expectedResponse.getSpaceId(), actualResponse.getSpaceId());
//        assertEquals(expectedResponse.getSpaceName(), actualResponse.getSpaceName());
//        assertEquals(expectedResponse.getSpaceLocation(), actualResponse.getSpaceLocation());
//        assertEquals(expectedResponse.getSpaceSize(), actualResponse.getSpaceSize());
//        assertEquals(expectedResponse.getSpacePrice(), actualResponse.getSpacePrice());
//        assertEquals(expectedResponse.getSpaceImage(), actualResponse.getSpaceImage());
//        assertEquals(expectedResponse.getAvailability(), actualResponse.getAvailability());
//        // You may add assertions for owner if needed
    }

    @Test
    void testEditSpace() {
        // Mock dependencies
//        SpaceService spaceService = mock(SpaceService.class);
//        EditSpaceRequest editSpaceRequest = new EditSpaceRequest();
//        editSpaceRequest.setSpaceId("123");
//        editSpaceRequest.setSpaceName("Updated Space");
//        editSpaceRequest.setSpaceLocation("Updated Location");
//        editSpaceRequest.setSpaceSize(200.0);
//        editSpaceRequest.setSpacePrice(100.0);
//        editSpaceRequest.setSpaceImage("updated_image.png");
//        editSpaceRequest.setAvailability("Unavailable");
//
//        SpaceResponse expectedResponse = new SpaceResponse();
//        expectedResponse.setSpaceId(editSpaceRequest.getSpaceId());
//        expectedResponse.setSpaceName(editSpaceRequest.getSpaceName());
//        expectedResponse.setSpaceLocation(editSpaceRequest.getSpaceLocation());
//        expectedResponse.setSpaceSize(editSpaceRequest.getSpaceSize());
//        expectedResponse.setSpacePrice(editSpaceRequest.getSpacePrice());
//        expectedResponse.setSpaceImage(editSpaceRequest.getSpaceImage());
//        expectedResponse.setOwner(editSpaceRequest.getOwner());
//        expectedResponse.setAvailability(editSpaceRequest.getAvailability());
//
//        // Test
//        SpaceResponse actualResponse = spaceService.editSpace(editSpaceRequest);
//
//        // Assertion
//        assertNotNull(actualResponse);
//        assertEquals(expectedResponse.getSpaceId(), actualResponse.getSpaceId());
//        assertEquals(expectedResponse.getSpaceName(), actualResponse.getSpaceName());
//        assertEquals(expectedResponse.getSpaceLocation(), actualResponse.getSpaceLocation());
//        assertEquals(expectedResponse.getSpaceSize(), actualResponse.getSpaceSize());
//        assertEquals(expectedResponse.getSpacePrice(), actualResponse.getSpacePrice());
//        assertEquals(expectedResponse.getSpaceImage(), actualResponse.getSpaceImage());
//        assertEquals(expectedResponse.getAvailability(), actualResponse.getAvailability());
//        // You may add assertions for owner if needed
    }

    @Test
    void testDeleteSpace() {
        // Mock dependencies
//        SpaceService spaceService = mock(SpaceService.class);
//        SpaceResponse expectedResponse = new SpaceResponse();
//        expectedResponse.setSpaceId("123");
//
//        // Test
//        SpaceResponse actualResponse = spaceService.deleteSpace("123");
//
//        // Assertion
//        assertNotNull(actualResponse);
//        assertEquals(expectedResponse.getSpaceId(), actualResponse.getSpaceId());
    }
}
