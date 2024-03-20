package com.example.backend.dtos.Report;

import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.dtos.User.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {
    private String reportId;
    private String reportType;
    private String reportDate;
    private String content;
    private UserResponse reporter;
    private UserResponse reported;
    private SpaceResponse reportedSpace;
}
