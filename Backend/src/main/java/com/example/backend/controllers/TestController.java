package com.example.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        //String customData = (String) request.getAttribute("userEmail");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String val2 = SecurityContextHolder.getContext().getAuthentication().toString();
               return "User email: " + username;
    }

}