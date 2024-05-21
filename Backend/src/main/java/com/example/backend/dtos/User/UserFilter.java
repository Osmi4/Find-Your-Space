package com.example.backend.dtos.User;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFilter {
    @Nullable
    @Email(message = "Email should be valid")
    private String email;
    @Nullable
    private String contactInfo;
    @Nullable
    private String firstName;
    @Nullable
    private String lastName;
}
