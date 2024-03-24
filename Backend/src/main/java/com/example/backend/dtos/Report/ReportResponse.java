package com.example.backend.dtos.Report;
import com.example.backend.dtos.Space.SpaceResponse;
import com.example.backend.dtos.User.UserResponse;
import com.example.backend.entity.User;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
public class ReportResponse {
    private String reportId;
    private ReportType reportType;
    private ReportStatus reportStatus;
    private String reportContent;
    private Date reportDateTime;
    private User reporter;
    private Optional<UserResponse> reportedUser;
    private Optional<SpaceResponse> reportedSpace;

    public ReportResponse(String reportId, ReportType reportType, ReportStatus reportStatus,
                          String reportContent, Date reportDateTime, User reporter,
                          Optional<UserResponse> reportedUser, Optional<SpaceResponse> reportedSpace) {
        this.reportId = reportId;
        this.reportType = reportType;
        this.reportStatus = reportStatus;
        this.reportContent = reportContent;
        this.reportDateTime = reportDateTime;
        this.reporter = reporter;

        switch (reportType) {
            case USER:
                if (reportedUser.isPresent()) {
                    this.reportedUser = reportedUser;
                    this.reportedSpace = Optional.empty();
                } else {
                    throw new IllegalArgumentException("Reported user must be present for USER_REPORT");
                }
                break;
            case SPACE:
                if (reportedSpace.isPresent()) {
                    this.reportedSpace = reportedSpace;
                    this.reportedUser = Optional.empty();
                } else {
                    throw new IllegalArgumentException("Reported space must be present for SPACE_REPORT");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid report type");
        }
    }
}
