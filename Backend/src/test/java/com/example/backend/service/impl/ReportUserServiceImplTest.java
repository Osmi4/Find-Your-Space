package com.example.backend.service.impl;

import com.example.backend.dtos.Report.AddReportRequest;
import com.example.backend.dtos.Report.ReportResponse;
import com.example.backend.service.ReportSpaceService;
import com.example.backend.service.ReportUserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReportUserServiceImplTest {

    @Test
    void testAddReport() {
        // Mock dependencies
        ReportUserService reportUserService = mock(ReportUserService.class);
        AddReportRequest addReportRequest = new AddReportRequest();
        addReportRequest.setReportType("spam");
        addReportRequest.setContent("Inappropriate content");
        addReportRequest.setReportedId("456");

        ReportResponse expectedResponse = new ReportResponse();
        expectedResponse.setReportId("123");
        expectedResponse.setReportType(addReportRequest.getReportType());
        expectedResponse.setContent(addReportRequest.getContent());

        // Test
        ReportResponse actualResponse = reportUserService.addReport(addReportRequest);

        // Assertion
        assertNotNull(actualResponse);
        assertEquals(expectedResponse.getReportId(), actualResponse.getReportId());
        assertEquals(expectedResponse.getReportType(), actualResponse.getReportType());
        assertEquals(expectedResponse.getContent(), actualResponse.getContent());
    }
}
