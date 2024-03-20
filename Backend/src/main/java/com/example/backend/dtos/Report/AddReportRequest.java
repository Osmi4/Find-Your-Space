package com.example.backend.dtos.Report;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddReportRequest {
    private String reportType;
    private String content;
    private String reportedId;
}
