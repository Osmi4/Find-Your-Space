package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rating" , uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ratingId;

    @Column(
            name = "score",
            nullable = false
    )
    private double score;

    @Column(
            name = "comment",
            nullable = false
    )
    private String comment;

    @Column(
            name = "date_added",
            nullable = false
    )
    private String dateAdded;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    private Space space;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

}
