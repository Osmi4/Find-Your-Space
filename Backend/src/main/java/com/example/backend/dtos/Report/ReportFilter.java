package com.example.backend.dtos.Report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportFilter {
    private Date startDate;
    private Date endDate;
    private String content;
    private ReportType reportType;
    private ReportStatus reportStatus;
    private String reporterId;
    private String reportedId;
}
