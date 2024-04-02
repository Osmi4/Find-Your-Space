package com.example.backend.service.impl;

import com.example.backend.dtos.User.UserFilter;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Test
    void testGetUser() {
        // Mock dependencies
        UserService userService = mock(UserService.class);
        UserResponse expectedResponse = new UserResponse();
        expectedResponse.setUserId("123");

        // Test
        UserResponse actualResponse = userService.getUserByUserId("123");

        // Assertion
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getUserId(), actualResponse.getUserId());
    }

    @Test
    void testGetUsers() {
        // Mock dependencies
        UserService userService = mock(UserService.class);
        UserFilter userFilter = new UserFilter();
        List<UserResponse> expectedResponse = new ArrayList<>();

        // Test
        List<UserResponse> actualResponse = userService.getUsersByFilters(userFilter);

        // Assertion
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.size(), actualResponse.size());
    }
}
