package com.example.backend.service;

import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportResponse;

public interface ReportUserService {
    ReportResponse addReport(AddReportRequest addReportRequest);
}
