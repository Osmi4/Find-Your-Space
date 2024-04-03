package com.example.backend.service.impl;

import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportResponse;
import com.example.backend.dtos.Report.ReportStatus;
import com.example.backend.entity.Report;
import com.example.backend.repository.ReportRepository;
import com.example.backend.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ReportServiceImplTest {
    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addReport_NullRequest_ExceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            reportService.addReport(null);
        });
    }

    @Test
    void addReport_ValidRequest_ReportAddedToDatabase() {
        AddReportRequest request = new AddReportRequest(/* populate request with data */);
        Report savedReport = new Report(/* populate saved report with data */);
        when(reportRepository.save(any())).thenReturn(savedReport);

        ReportResponse response = reportService.addReport(request);

        assertNotNull(response);
        assertEquals(savedReport.getReportId(), response.getReportId());
        assertEquals(ReportStatus.PENDING, savedReport.getReportStatus());
    }

    @Test
    void getReportById_ValidId_ReportReturned() {
        String id = "valid-id";
        Report report = new Report(/* populate report with data */);
        when(reportRepository.findById(id)).thenReturn(Optional.of(report));

        ReportResponse response = reportService.getReportById(id);

        assertNotNull(response);
        assertEquals(report.getReportId(), response.getReportId());
    }

    @Test
    void getReportById_InvalidId_ExceptionThrown() {
        String id = "invalid-id";
        when(reportRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            reportService.getReportById(id);
        });
    }
}
