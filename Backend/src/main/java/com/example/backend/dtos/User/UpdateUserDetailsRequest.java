package com.example.backend.dtos.User;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDetailsRequest {
    @NotBlank(message = "First name cannot be blank")
    @Length(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    @Length(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    @NotBlank(message = "Contact information cannot be blank")
    private String contactInfo;
    @Length(min = 2, max = 50, message = "Bank account number must be between 2 and 50 characters")
    private String bankAccountNumber;

}
