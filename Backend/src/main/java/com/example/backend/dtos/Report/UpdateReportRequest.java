package com.example.backend.dtos.Report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReportRequest {
    private String reportId;
    private String reportType;
    private String comment;
    private String reportedId;
}
