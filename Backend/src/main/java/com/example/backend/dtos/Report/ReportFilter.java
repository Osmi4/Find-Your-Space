package com.example.backend.dtos.Report;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReportFilter {
    private String reportId;
    private String startDate;
    private String endDate;
    private String content;
    private String reportType;
    private String reportedId;
}
