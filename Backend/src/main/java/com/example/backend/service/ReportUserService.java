package com.example.backend.service;

import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportFilter;
import com.example.backend.dtos.Report.ReportResponse;
import com.example.backend.dtos.Report.UpdateReportRequest;

import java.util.List;

public interface ReportUserService {
    ReportResponse addReport(AddReportRequest addReportRequest);

    ReportResponse getReport(String id , String type);

    List<ReportResponse> filterReports(ReportFilter reportFilter);

    ReportResponse updateReport(UpdateReportRequest updateReportRequest);
}
