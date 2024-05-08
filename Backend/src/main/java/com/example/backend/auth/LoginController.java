package com.example.backend.auth;

import com.example.backend.dtos.Auth.LoginDto;
import com.example.backend.dtos.Auth.PasswordChange;
import com.example.backend.dtos.Auth.RegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationService authenticationService;

    @GetMapping("/oauth2/code/google")
    public ResponseEntity<String> register() {
        return ResponseEntity.ok("works");
    }


}