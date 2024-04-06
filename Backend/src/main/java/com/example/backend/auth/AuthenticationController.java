package com.example.backend.auth;

import com.example.backend.dtos.Auth.LoginDto;
import com.example.backend.dtos.Auth.PasswordChange;
import com.example.backend.dtos.Auth.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    //dodaj valid
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(authenticationService.register(registerDto));
    }
    //valid
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authenticationService.authenticate(loginDto));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChange passwordChange) {
        if (authenticationService.changePassword(passwordChange)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //forgot password przez maila metoda
}
