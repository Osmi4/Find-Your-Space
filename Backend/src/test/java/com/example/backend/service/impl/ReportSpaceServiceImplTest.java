package com.example.backend.service.impl;

import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportResponse;
import com.example.backend.service.ReportSpaceService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportSpaceServiceImplTest {

    @Test
    void testAddReport() {
        // Mock dependencies
        ReportSpaceService reportSpaceService = mock(ReportSpaceService.class);
        AddReportRequest addReportRequest = new AddReportRequest();
        addReportRequest.setReportType("spam");
        addReportRequest.setContent("Inappropriate content");
        addReportRequest.setReportedId("456");

        ReportResponse expectedResponse = new ReportResponse();
        expectedResponse.setReportId("123");
        expectedResponse.setReportType(addReportRequest.getReportType());
        expectedResponse.setContent(addReportRequest.getContent());

        // Test
        ReportResponse actualResponse = reportSpaceService.addReport(addReportRequest);

        // Assertion
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getReportId(), actualResponse.getReportId());
        assertEquals(expectedResponse.getReportType(), actualResponse.getReportType());
        assertEquals(expectedResponse.getContent(), actualResponse.getContent());
        // You may add assertions for reporter, reported, and reportedSpace if needed
    }
}
