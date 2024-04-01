package com.example.backend.entity;

import com.example.backend.enums.Status;
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
    private Date startDateTime;

    @Column(
            name = "end_date_time",
            nullable = false
    )
    private Date endDateTime;
    @Column(
            name = "cost",
            nullable = false
    )
    private double cost;
    @Column(
            name = "date_added",
            nullable = false
    )
    private Date dateAdded;
    @Column(
            name = "date_updated",
            nullable = false
    )
    private Date dateUpdated;
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

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Payment payment;
}
