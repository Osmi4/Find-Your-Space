package com.example.backend.auth;

import com.example.backend.config.JwtService;
import com.example.backend.dtos.Auth.LoginDto;
import com.example.backend.dtos.Auth.PasswordChange;
import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.enums.Role;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterDto registerDto) {
        var user = User.builder()
                .userName(registerDto.getUserName())
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        var user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public boolean changePassword(PasswordChange passwordChange) {
        String oldPassword = passwordChange.getOldPassword();
        String newPassword = passwordChange.getNewPassword();
        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        String username = currentAuth.getName();

        Authentication reAuth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        if (reAuth.isAuthenticated()) {
            User user = (User) reAuth.getPrincipal();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            Authentication newAuth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, newPassword));
            SecurityContextHolder.getContext().setAuthentication(newAuth);

            return true;
        } else {
            return false;
        }
    }
}
