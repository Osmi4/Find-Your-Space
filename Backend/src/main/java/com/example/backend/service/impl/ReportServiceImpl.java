package com.example.backend.service.impl;

import com.example.backend.dtos.Report.*;
import com.example.backend.entity.Report;
import com.example.backend.entity.Space;
import com.example.backend.entity.User;
import com.example.backend.repository.ReportRepository;
import com.example.backend.repository.SpaceRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.ReportService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final SpaceRepository spaceRepository;

    private final UserServiceImpl userServiceImpl;


    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository, SpaceRepository spaceRepository, UserServiceImpl userServiceImpl) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.spaceRepository = spaceRepository;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public ReportResponse addReport(AddReportRequest addReportRequest) {
        Report report = mapReportRequestToReport(addReportRequest);
        report.setReportStatus(ReportStatus.PENDING);
        report.setReportDateTime(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        Report savedReport=reportRepository.save(report);
        System.out.println(savedReport);

        return mapReportToReportResponse(savedReport);
    }

    @Override
    public ReportResponse getReportById(String id) {
        Report report = reportRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Report not found"));
        return mapReportToReportResponse(report);
    }

//    @Override
//    public List<ReportResponse> getReportsByFilters(ReportFilter reportFilter) {
//        List<Report> reports = reportRepository.findByReportDateTimeBetweenAndReportContentContainingAndReportTypeAndReportStatusAndReporter_IdAndReportedUser_Id(
//                reportFilter.getStartDate(), reportFilter.getEndDate(), reportFilter.getContent(), reportFilter.getReportType(),
//                reportFilter.getReportStatus(), reportFilter.getReporterId(), reportFilter.getReportedId());
//
//        return reports.stream().map(this::mapReportToReportResponse).toList();
//    }

    @Override
    public ReportResponse updateReport(UpdateReportRequest updateReportRequest) {
        Report report = reportRepository.findById(updateReportRequest.getReportId()).orElseThrow(() -> new NoSuchElementException("Report not found"));
        System.out.println(report.getReportId());
        report.setReportStatus(updateReportRequest.getReportStatus());
        reportRepository.save(report);
        return mapReportToReportResponse(report);
    }

    public Report mapReportRequestToReport(AddReportRequest addReportRequest) {
        Report report = new Report();
        report.setReportType(addReportRequest.getReportType());
        report.setReportContent(addReportRequest.getReportContent());
        report.setReporter((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        switch (addReportRequest.getReportType()) {
            case USER:
                Optional<User> userOptional = userRepository.findByUserId(addReportRequest.getReportedId());
                if (userOptional != null) {
                    report.setReportedUser(userOptional.get());
                } else {
                    throw new NoSuchElementException("User not found");
                }
                break;
            case SPACE:
                Optional<Space> spaceOptional = spaceRepository.findById("e60e46c1-9be3-418c-a6f4-bc726b2dece2");
                if (spaceOptional.isPresent()) {
                    report.setReportedSpace(spaceOptional.get());
                } else {
                    throw new NoSuchElementException("Space not found");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid report type");
        }
        return report;
    }

    public ReportResponse mapReportToReportResponse(Report report) {
        return new ReportResponse(
                report.getReportId(),
                report.getReportType(),
                report.getReportStatus(),
                report.getReportContent(),
                report.getReportDateTime(),
                report.getReporter(),
                report.getReportedUser() != null ? Optional.ofNullable(userServiceImpl.mapUserToUserResponse(report.getReportedUser())) : null,
                null
        );
    }

}
