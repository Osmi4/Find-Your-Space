package com.example.backend.service;

import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.dtos.User.UpdateUserDetailsRequest;
import com.example.backend.dtos.User.UpdateUserRequest;
import com.example.backend.dtos.User.UserFilter;
import com.example.backend.dtos.User.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponse getUserByUserId(String id);
    UserResponse getUserByUserEmail(String email);

    Page<UserResponse> getUsersByFilters(UserFilter userFilter, Pageable pageable);

    void updateUser(String userId,UpdateUserRequest updateUserRequest);
    void updateUserDetails(String userId, UpdateUserDetailsRequest updateUserRequestDetails);

    UserResponse getMyDetails();
    UserResponse registerWithoutDuplicateCheck(RegisterDto registerDto);
}
