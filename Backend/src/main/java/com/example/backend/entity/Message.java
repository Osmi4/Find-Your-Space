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
@Table(name = "message", uniqueConstraints = {@UniqueConstraint(columnNames = "message_id")})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String messageId;

    @Column(
            name = "message_content",
            nullable = false
    )
    private String messageContent;

    @Column(
            name = "message_date_time",
            nullable = false
    )
    private String messageDateTime;

    @Column(
            name = "message_destination_email",
            nullable = false
    )
    private String messageDestinationEmail;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;
}
