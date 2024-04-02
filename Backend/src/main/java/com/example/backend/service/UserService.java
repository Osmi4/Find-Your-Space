package com.example.backend.service;

import com.example.backend.dtos.User.UpdateUserRequest;
import com.example.backend.dtos.User.UserFilter;
import com.example.backend.dtos.User.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getUserByUserId(String id);
    List<UserResponse> getUsersByFilters(UserFilter userFilter);

    void updateUser(String userId,UpdateUserRequest updateUserRequest);
}
