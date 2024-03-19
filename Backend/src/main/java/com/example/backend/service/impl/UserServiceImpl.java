package com.example.backend.service.impl;

import com.example.backend.dtos.User.UserFilter;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserResponse getUser(String id) {
        return null;
    }

    @Override
    public List<UserResponse> getUsers(UserFilter userFilter) {
        return null;
    }
}
