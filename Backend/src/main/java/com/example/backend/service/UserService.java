package com.example.backend.service;

import com.example.backend.dtos.User.UpdateUserRequest;
import com.example.backend.dtos.User.UserFilter;
import com.example.backend.dtos.User.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponse getUserByUserId(String id);
    Page<UserResponse> getUsersByFilters(UserFilter userFilter, Pageable pageable);

    void updateUser(String userId,UpdateUserRequest updateUserRequest);
}
