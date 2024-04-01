package com.example.backend.controllers;

import com.example.backend.dtos.User.UpdateUserRequest;
import com.example.backend.dtos.User.UserFilter;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable String id){
        return userService.getUserByUserId(id);
    }

    @GetMapping("/")
    public List<UserResponse> getUsers( @RequestBody UserFilter userFilter) {
        return userService.getUsersByFilters(userFilter);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserRequest user) {
        userService.updateUser(id, user);
        return new ResponseEntity("userService.updateUser(id, user)", HttpStatus.OK);
    }
}
