package com.example.backend.entity;

import com.example.backend.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "payment", uniqueConstraints = {@UniqueConstraint(columnNames = "payment_id")})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentId;

    @Column(
            name = "amount",
            nullable = false
    )
    private double amount;

    @Column(
            name = "payment_date_time",
            nullable = false
    )
    private String paymentDateTime;

    @Column(
            name = "payment_status",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @ToString.Exclude
    private User client;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @ToString.Exclude
    private User owner;

    @OneToOne
    @JoinColumn(name = "booking_id" , nullable = true)
    @ToString.Exclude
    private Booking booking;
}
