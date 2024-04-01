package com.example.backend.controllers;

import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportFilter;
import com.example.backend.dtos.Report.ReportResponse;
import com.example.backend.dtos.Report.UpdateReportRequest;
import com.example.backend.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/")
    public ReportResponse addReport(@RequestBody AddReportRequest addReportRequest){
        return reportService.addReport(addReportRequest);
    }
    @GetMapping("/")
    public List<ReportResponse> getReports(@RequestBody ReportFilter reportFilter){
        System.out.println("reportFilter");
        return reportService.getReportsByFilters(reportFilter);
    }
    @GetMapping("/{id}")
    public ReportResponse getReportById(@PathVariable String id){
        return reportService.getReportById(id);
    }

    @PutMapping("/")
    public ReportResponse updateReport(@RequestBody UpdateReportRequest updateReportRequest){
        return reportService.updateReport(updateReportRequest);
    }
}
