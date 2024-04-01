package com.example.backend.dtos.User;

import jakarta.annotation.Nullable;
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
    private String userName;
    @Nullable
    private String userEmail;
    @Nullable
    private String contactInfo;
    @Nullable
    private String firstName;
    @Nullable
    private String lastName;
}
