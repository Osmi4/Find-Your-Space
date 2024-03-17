package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking" , uniqueConstraints = {@UniqueConstraint(columnNames = "booking_id")})
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookingId;

    @Column(
            name = "start_date_time",
            nullable = false
    )
    private String startDateTime;

    @Column(
            name = "end_date_time",
            nullable = false
    )
    private String endDateTime;

    @Column(
            name = "status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "client" , nullable = false)
    private User client;

    @ManyToOne
    @JoinColumn(name = "space" , nullable = false)
    private Space space;
}
