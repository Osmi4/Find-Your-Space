package com.example.backend.dtos.Report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportFilter {
    private String reportId;
    private String startDate;
    private String endDate;
    private String content;
    private String reportType;
    private String reportedId;
}
