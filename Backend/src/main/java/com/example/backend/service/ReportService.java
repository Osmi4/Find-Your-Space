package com.example.backend.service;

import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportFilter;
import com.example.backend.dtos.Report.ReportResponse;
import com.example.backend.dtos.Report.UpdateReportRequest;

import java.util.List;

public interface ReportService {
    ReportResponse addReport(AddReportRequest addReportRequest);

    ReportResponse getReportById(String id);

//    List<ReportResponse> getReportsByFilters(ReportFilter reportFilter);

    ReportResponse updateReport(UpdateReportRequest updateReportRequest);
}
