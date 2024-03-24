package com.example.backend.controllers;

import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
