package com.example.backend.dtos.Auth;

import com.example.backend.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Contact information cannot be blank")
    private String contactInfo;

    @NotBlank(message = "First name cannot be blank")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "First name must start with a capital letter and contain only letters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Last name must start with a capital letter and contain only letters")
    private String lastName;
}
