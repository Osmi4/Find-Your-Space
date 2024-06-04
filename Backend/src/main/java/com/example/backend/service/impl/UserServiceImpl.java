package com.example.backend.service.impl;

import com.example.backend.auth.AuthenticationResponse;
import com.example.backend.autoMapper.UserMapper;
import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.dtos.User.UpdateUserDetailsRequest;
import com.example.backend.dtos.User.UpdateUserRequest;
import com.example.backend.dtos.User.UserFilter;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.User;
import com.example.backend.enums.Role;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    public UserResponse getUserByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found!", "email", email));
        UserResponse userResponse = UserMapper.INSTANCE.userToUserResponse(user);
        if(userResponse==null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not correctly converted");
        }
        return userResponse;    }

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
        int affectedRows= userRepository.patchUser(userId, updateUserRequest.getEmail(), updateUserRequest.getFirstName(), updateUserRequest.getLastName() , updateUserRequest.getContactInfo());
        if(affectedRows==0){
            throw new ResourceNotFoundException("User not found!", "userId", userId);
        }
    }

    @Override
    public void updateUserDetails(UpdateUserDetailsRequest updateUserRequestDetails) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found!", "email", SecurityContextHolder.getContext().getAuthentication().getName()));
        String userId = user.getUserId();
        User userToUpdate = userRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!", "userId", userId));

        if(!userToUpdate.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to change other users details");
        }
        userToUpdate.setBankAccount(updateUserRequestDetails.getBankAccountNumber());
        userToUpdate.setContactInfo(updateUserRequestDetails.getContactInfo());
        userToUpdate.setFirstName(updateUserRequestDetails.getFirstName());
        userToUpdate.setLastName(updateUserRequestDetails.getLastName());
        userToUpdate.setDetailsConfigured(true);

        int affectedRows = userRepository.patchUserDetails(userId, updateUserRequestDetails.getContactInfo(), updateUserRequestDetails.getBankAccountNumber(), updateUserRequestDetails.getFirstName() , updateUserRequestDetails.getLastName() ,true);
        if(affectedRows==0){
            throw new ResourceNotFoundException("User not found!", "userId", userId);
        }
    }

    @Override
    public UserResponse getMyDetails() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found!", "email", SecurityContextHolder.getContext().getAuthentication().getName()));
        return UserMapper.INSTANCE.userToUserResponse(user);
    }
    public UserResponse registerWithoutDuplicateCheck(RegisterDto registerDto) {
        var user = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .pictureUrl(registerDto.getPictureUrl())
                .role(Role.USER)
                .detailsConfigured(false)
                .build();

        User userSaved= userRepository.save(user);

        return UserMapper.INSTANCE.userToUserResponse(userSaved);
    }

    @Override
    public String getBankAccount() {
        //User user = userRepository.findByUserId(id).orElseThrow(() -> new ResourceNotFoundException("User not found!", "userId", id));
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found!", "email", SecurityContextHolder.getContext().getAuthentication().getName()));
        return user.getBankAccount();
    }

    @Override
    public String getRole() {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found!", "email", SecurityContextHolder.getContext().getAuthentication().getName()));
        return user.getRole().toString();
    }
}
