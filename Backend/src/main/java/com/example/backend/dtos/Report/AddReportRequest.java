package com.example.backend.dtos.Report;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddReportRequest {
    @NotNull(message = "reportType is required")
    private ReportType reportType;
    @NotBlank(message = "reportContent is required")
    private String reportContent;
    @NotBlank(message = "reportedId is required")
    private String reportedId;
}
