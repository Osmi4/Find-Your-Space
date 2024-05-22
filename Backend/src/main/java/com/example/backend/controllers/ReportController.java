package com.example.backend.controllers;

import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportFilter;
import com.example.backend.dtos.Report.ReportResponse;
import com.example.backend.dtos.Report.UpdateReportRequest;
import com.example.backend.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<Page<ReportResponse>> getReports(@RequestBody ReportFilter reportFilter, @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(reportService.getReportsByFilters(reportFilter, pageable));
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
