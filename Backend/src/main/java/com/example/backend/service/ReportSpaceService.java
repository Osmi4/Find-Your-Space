package com.example.backend.service;

import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportResponse;

public interface ReportSpaceService {
    ReportResponse addReport(AddReportRequest addReportRequest);
}
