package com.example.backend.dtos.Space;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateRequest {
    @Future(message = "Start date must be in the future")
    @NotBlank(message = "Start date cannot be null")
    private Date startDateTime;

    @Future(message = "End date must be in the future")
    @NotBlank(message = "End date cannot be null")
    private Date endDateTime;
}
