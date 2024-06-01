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
    @NotBlank(message = "Contact information cannot be blank")
    private String contactInfo;
    @Length(min = 2, max = 50, message = "Bank account number must be between 2 and 50 characters")
    private String bankAccountNumber;

}
