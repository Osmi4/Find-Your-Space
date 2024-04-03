package com.example.backend.entity;

import com.example.backend.dtos.Report.ReportStatus;
import com.example.backend.dtos.Report.ReportType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Optional;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "report" , uniqueConstraints = {@UniqueConstraint(columnNames = "report_id")})
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reportId;

    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    @Column(name = "report_status", nullable = false)
    private ReportStatus reportStatus = ReportStatus.PENDING;

    @Column(name = "report_content", nullable = false)
    private String reportContent;

    @Column(name = "report_date_time", nullable = false)
    private Date reportDateTime;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    @ToString.Exclude
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "reported_user_id", nullable = true)
    @ToString.Exclude
    private User reportedUser;

    @ManyToOne
    @JoinColumn(name = "reported_space_id", nullable = true)
    @ToString.Exclude
    private Space reportedSpace;

}
