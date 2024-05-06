package com.example.backend.service;

import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportFilter;
import com.example.backend.dtos.Report.ReportResponse;
import com.example.backend.dtos.Report.UpdateReportRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportService {
    ReportResponse addReport(AddReportRequest addReportRequest);

    ReportResponse getReportById(String id);

    Page<ReportResponse> getReportsByFilters(ReportFilter reportFilter, Pageable pageable);

    ReportResponse updateReport(UpdateReportRequest updateReportRequest);

}
