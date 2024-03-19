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
@Table(name = "report_user", uniqueConstraints = {@UniqueConstraint(columnNames = "report_user_id")})
public class ReportUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reportUsertId;

    @Column(
            name = "report_user_content",
            nullable = false
    )
    private String reportUserContent;

    @Column(
            name = "report_user_date_time",
            nullable = false
    )
    private String reportUserDateTime;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "reported_id", nullable = false)
    private User reported;


}
