package com.example.backend.service.impl;

import com.example.backend.dtos.User.UpdateUserRequest;
import com.example.backend.dtos.User.UserFilter;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.Report;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserResponse getUserByUserId(String id) {
        User user = userRepository.findByUserId(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        return mapUserToUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsers(UserFilter userFilter) {
        return null;
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest updateUserRequest) {
        return null;
    }

    public UserResponse mapUserToUserResponse(User user) {
        return new UserResponse(user.getUserId(), user.getUsername(), user.getEmail(), user.getContactInfo(),user.getFirstName(),user.getLastName());
    }


}
