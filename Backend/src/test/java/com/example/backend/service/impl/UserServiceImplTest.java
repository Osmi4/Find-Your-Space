package com.example.backend.service.impl;

import com.example.backend.auth.AuthenticationResponse;
import com.example.backend.auth.AuthenticationService;
import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.dtos.User.UpdateUserRequest;
import com.example.backend.dtos.User.UserFilter;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
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

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceImplTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;
    private RegisterDto registerDto;
    User user;
    @BeforeEach
    public void setUp() {
        registerDto = new RegisterDto();
        registerDto.setEmail("test@ggmail.com");
        registerDto.setFirstName("John");
        registerDto.setLastName("Doe");
        registerDto.setPassword("password");
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto);
        user = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
    @Test
    void testGetUserByUserId_Success() {
        UserResponse userResponse = userService.getUserByUserId(user.getUserId());
        assertEquals(userResponse.getUserId(), user.getUserId());
        assertEquals(userResponse.getUserEmail(), user.getEmail());
        assertEquals(userResponse.getContactInfo(), user.getContactInfo());
        assertEquals(userResponse.getFirstName(), user.getFirstName());
        assertEquals(userResponse.getLastName(), user.getLastName());
    }
    @Test
    void getUsersByFilters_Success() {
        UserFilter userFilter = new UserFilter();
        userFilter.setFirstName(user.getFirstName());
        Pageable pageable = PageRequest.of(0, 10);
        Page<UserResponse> userResponsePage = userService.getUsersByFilters(userFilter, pageable);
        assertEquals(userResponsePage.getContent().get(0).getUserId(), user.getUserId());
        assertEquals(userResponsePage.getContent().get(0).getUserId(), user.getUserId());
    }
    @Test
    void updateUser_Success() {
        /// napraw zeby nie bylo tzreba wsyztskiego uzupelniac w updaterequest
    }
}