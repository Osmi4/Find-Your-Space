package com.example.backend.repository;

import com.example.backend.dtos.Report.ReportStatus;
import com.example.backend.dtos.Report.ReportType;
import com.example.backend.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, String> {

    Optional<Report> findById(String id);

//    List<Report> findByReportDateTimeBetweenAndReportContentContainingAndReportTypeAndReportStatusAndReporter_IdAndReportedUser_Id(
//            Date startDate, Date endDate, String content, ReportType reportType, ReportStatus reportStatus,
//            String reporterId, String reportedId);
}
