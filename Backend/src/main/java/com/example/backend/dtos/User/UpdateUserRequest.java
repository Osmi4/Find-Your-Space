package com.example.backend.dtos.User;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @NotBlank(message = "userName is required")
    private String userName;
    @NotBlank(message = "userEmail is required")
    private String userEmail;
    @NotBlank(message = "contactInfo is required")
    private String contactInfo;
    @NotBlank(message = "firstName is required")
    private String firstName;
    @NotBlank(message = "lastName is required")
    private String lastName;
}
