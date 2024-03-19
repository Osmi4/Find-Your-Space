package com.example.backend.dtos.Report;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddReportRequest {
    private String reportType;
    private String content;
    private String reportedId;
}
