package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "report_space", uniqueConstraints = {@UniqueConstraint(columnNames = "report_space_id")})
public class ReportSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reportSpacetId;

    @Column(name = "report_space_content", nullable = false)
    private String reportSpaceContent;

    @Column(name = "report_space_date_time", nullable = false)
    private String reportSpaceDateTime;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "reported_id", nullable = false)
    private Space reported;


}
