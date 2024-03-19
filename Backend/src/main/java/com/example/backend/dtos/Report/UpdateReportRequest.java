package com.example.backend.dtos.Report;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateReportRequest {
    private String reportId;
    private String reportType;
    private String comment;
    private String reportedId;
}
