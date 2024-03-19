package com.example.backend.dtos.Auth;

import com.example.backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String userName;
    private String email;
    private String password;
    private Role role;
    private String contactInfo;
    private String firstName;
    private String lastName;
}
