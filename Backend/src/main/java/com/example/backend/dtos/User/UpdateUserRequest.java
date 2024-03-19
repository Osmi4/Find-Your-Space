package com.example.backend.dtos.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserRequest {
    private String userName;
    private String userEmail;
    private String contactInfo;
    private String firstName;
    private String lastName;
    private String bankAccount;
}
