package com.example.backend.dtos.Report;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportFilter {
    @Nullable
    private ReportType reportType;
    @Nullable
    private ReportStatus reportStatus;
}
