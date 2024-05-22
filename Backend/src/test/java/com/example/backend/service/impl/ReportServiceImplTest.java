package com.example.backend.service.impl;

import com.example.backend.auth.AuthenticationResponse;
import com.example.backend.auth.AuthenticationService;
import com.example.backend.dtos.Auth.RegisterDto;
import com.example.backend.dtos.Report.*;
import com.example.backend.dtos.Space.AddSpaceRequest;
import com.example.backend.dtos.Space.EditSpaceRequest;
import com.example.backend.entity.User;
import com.example.backend.enums.SpaceType;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.ReportService;
import com.example.backend.service.SpaceService;
import jakarta.transaction.Transactional;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ReportServiceImplTest {
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReportService reportService;

    private RegisterDto registerDto;
    private RegisterDto registerDto2;

    @BeforeEach
    public void setUp() {
        registerDto = Instancio.create(RegisterDto.class);
        AuthenticationResponse authenticationResponse = authenticationService.register(registerDto);
        User user = userRepository.findByEmail(registerDto.getEmail()).orElse(null);
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        registerDto2 = Instancio.create(RegisterDto.class);
        AuthenticationResponse authenticationResponse2 = authenticationService.register(registerDto2);
    }
    @Test
    void addReport_Success(){
        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        AddReportRequest addReportRequest = AddReportRequest.builder()
                .reportType(ReportType.USER)
                .reportContent("This is a spam")
                .reportedId(user.getUserId())
                .build();
        ReportResponse reportResponse = reportService.addReport(addReportRequest);
        assertNotNull(reportResponse);
        assertEquals(ReportType.USER, reportResponse.getReportType());
        assertEquals("This is a spam", reportResponse.getReportContent());
        assertEquals(user.getUserId(), reportResponse.getReportedUser().get().getUserId());
    }

    @Test
    void getReportById_Success(){
        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        AddReportRequest addReportRequest = AddReportRequest.builder()
                .reportType(ReportType.USER)
                .reportContent("This is a spam")
                .reportedId(user.getUserId())
                .build();
        ReportResponse reportResponse = reportService.addReport(addReportRequest);
        ReportResponse reportResponse1 = reportService.getReportById(reportResponse.getReportId());
        assertNotNull(reportResponse1);
        assertEquals(reportResponse.getReportId(), reportResponse1.getReportId());
    }

    @Test
    void updateReport_Success(){
        User user = userRepository.findByEmail(registerDto2.getEmail()).orElse(null);
        AddReportRequest addReportRequest = AddReportRequest.builder()
                .reportType(ReportType.USER)
                .reportContent("This is a spam")
                .reportedId(user.getUserId())
                .build();
        ReportResponse reportResponse = reportService.addReport(addReportRequest);
        UpdateReportRequest updateReportRequest = UpdateReportRequest.builder()
                .reportId(reportResponse.getReportId())
                .reportStatus(ReportStatus.SOLVED)
                .build();
        ReportResponse reportResponse1 = reportService.updateReport(updateReportRequest);
        assertNotNull(reportResponse1);
        assertEquals(ReportStatus.SOLVED, reportResponse1.getReportStatus());
    }
}