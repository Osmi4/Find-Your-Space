package com.example.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        String customData = (String) request.getAttribute("userEmail");
        return "User email: " + customData;
    }

}