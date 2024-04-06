package com.example.backend.controllers;

import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportFilter;
import com.example.backend.dtos.Report.ReportResponse;
import com.example.backend.dtos.Report.UpdateReportRequest;
import com.example.backend.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ReportResponse> addReport(@Valid @RequestBody AddReportRequest addReportRequest){
        return new ResponseEntity(reportService.addReport(addReportRequest), HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<ReportResponse>> getReports(@RequestBody ReportFilter reportFilter){
        System.out.println("reportFilter");
        return ResponseEntity.ok(reportService.getReportsByFilters(reportFilter));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReportById(@PathVariable String id){
        return ResponseEntity.ok(reportService.getReportById(id));
    }
    //edycja biznesowych wartosci zmiana statusu zgloszenia
    @PutMapping("/")
    public ResponseEntity<ReportResponse> updateReport(@RequestBody UpdateReportRequest updateReportRequest){
        return ResponseEntity.ok(reportService.updateReport(updateReportRequest));
    }
}
