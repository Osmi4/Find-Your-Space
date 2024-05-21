package com.example.backend.service.impl;

import com.example.backend.autoMapper.UserMapper;
import com.example.backend.dtos.User.UpdateUserRequest;
import com.example.backend.dtos.User.UserFilter;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.User;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


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
        User user = userRepository.findByUserId(id).orElseThrow(() -> new ResourceNotFoundException("User not found!", "userId", id));
        UserResponse userResponse = UserMapper.INSTANCE.userToUserResponse(user);
        if(userResponse==null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not correctly converted");
        }
        return userResponse;
    }

    @Override
    public Page<UserResponse> getUsersByFilters(UserFilter userFilter, Pageable pageable) {
        Page<User> users = userRepository.findUsersByFilter(userFilter.getEmail(), userFilter.getFirstName(), userFilter.getLastName(),userFilter.getContactInfo(), pageable);
        return users.map(UserMapper.INSTANCE::userToUserResponse);
    }

    @Override
    public void updateUser(String userId,UpdateUserRequest updateUserRequest) {
        User userToUpdate = userRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!", "userId", userId));
        if(updateUserRequest.getEmail()==null || updateUserRequest.getEmail().isEmpty()){
            updateUserRequest.setEmail(userToUpdate.getEmail());
        }
        if(updateUserRequest.getContactInfo()==null || updateUserRequest.getContactInfo().isEmpty()){
            updateUserRequest.setContactInfo(userToUpdate.getContactInfo());
        }
        if(updateUserRequest.getFirstName()==null || updateUserRequest.getFirstName().isEmpty()){
            updateUserRequest.setFirstName(userToUpdate.getFirstName());
        }
        if(updateUserRequest.getLastName()==null || updateUserRequest.getLastName().isEmpty()){
            updateUserRequest.setLastName(userToUpdate.getLastName());
        }
        int affectedRows= userRepository.patchUser(userId, updateUserRequest.getEmail(), updateUserRequest.getContactInfo(), updateUserRequest.getFirstName(), updateUserRequest.getLastName());
        if(affectedRows==0){
            throw new ResourceNotFoundException("User not found!", "userId", userId);
        }
    }

}
