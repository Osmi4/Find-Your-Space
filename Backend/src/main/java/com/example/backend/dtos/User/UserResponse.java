package com.example.backend.dtos.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String userId;
    private String userName;
    private String userEmail;
    private String contactInfo;
    private String firstName;
    private String lastName;

}
