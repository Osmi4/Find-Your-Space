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
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id){
        return ResponseEntity.ok(userService.getUserByUserId(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getUsers( @RequestBody UserFilter userFilter) {
        return ResponseEntity.ok(userService.getUsersByFilters(userFilter));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @Valid @RequestBody UpdateUserRequest user) {
        userService.updateUser(id, user);
        return new ResponseEntity("User updated successfully!", HttpStatus.OK);
    }
    //change user details
}
