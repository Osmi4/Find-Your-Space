package com.example.backend.repository;

import com.example.backend.dtos.Report.ReportStatus;
import com.example.backend.dtos.Report.ReportType;
import com.example.backend.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, String> {

    Optional<Report> findById(String id);

    @Query("SELECT r FROM Report r WHERE " +
            "(:reportType IS NULL OR r.reportType = :reportType) AND " +
            "(:reportStatus IS NULL OR r.reportStatus = :reportStatus) "
    )
    Page<Report> findReportsByFilter(
            @Param("reportType") ReportType reportType,
            @Param("reportStatus") ReportStatus reportStatus,
            Pageable pageable
    );
}
