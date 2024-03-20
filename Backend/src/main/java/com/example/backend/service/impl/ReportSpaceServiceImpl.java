package com.example.backend.service.impl;

import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportFilter;
import com.example.backend.dtos.Report.ReportResponse;
import com.example.backend.dtos.Report.UpdateReportRequest;
import com.example.backend.service.ReportSpaceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportSpaceServiceImpl implements ReportSpaceService {
    @Override
    public ReportResponse addReport(AddReportRequest addReportRequest) {
        return null;
    }

    @Override
    public ReportResponse getReport(String id, String type) {
        return null;
    }

    @Override
    public List<ReportResponse> filterReports(ReportFilter reportFilter) {
        return null;
    }

    @Override
    public ReportResponse updateReport(UpdateReportRequest updateReportRequest) {
        return null;
    }
}
