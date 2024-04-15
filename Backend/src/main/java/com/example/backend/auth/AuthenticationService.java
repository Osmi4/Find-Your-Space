package com.example.backend.auth;

import com.example.backend.config.JwtService;
import com.example.backend.dtos.Auth.LoginDto;
import com.example.backend.dtos.Auth.PasswordChange;
import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.enums.Role;
import com.example.backend.entity.User;
import com.example.backend.exception.ApiRequestException;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterDto registerDto) {
        var user = User.builder()
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .contactInfo(registerDto.getContactInfo())
                .role(Role.USER)
                .build();
        Optional<User> userOptional = userRepository.findByEmail(registerDto.getEmail());
        if(userOptional.isPresent()){
            throw new ApiRequestException("User already exists");
        }
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

    public void changePassword(PasswordChange passwordChange) {
        // ACL to powteirdzenia czy on moze to zrobic
        String oldPassword = passwordChange.getOldPassword();
        String newPassword = passwordChange.getNewPassword();

        Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
        String username = currentAuth.getName();

        Optional<User> userToCheck = userRepository.findByEmail(username);
        User currentUser= new User();
        if(userToCheck.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "User not found");
        }
        else{
            currentUser = userToCheck.get();
        }

        String currentPassword = currentUser.getPassword();
        if(!passwordEncoder.matches(oldPassword, currentUser.getPassword())){
            throw new ApiRequestException("Old password is incorrect");
        }

        Authentication reAuth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        if (reAuth.isAuthenticated()) {
            User user = (User) reAuth.getPrincipal();
            user.setPassword(passwordEncoder.encode(newPassword));
            var saveValue = userRepository.save(user);
            if(saveValue==null){
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Password not changed");
            }
            Authentication newAuth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, newPassword));
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Password not changed");
        }
    }
}
