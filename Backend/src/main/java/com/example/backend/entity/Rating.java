package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "rating" , uniqueConstraints = {@UniqueConstraint(columnNames = "ratingId")})
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
    private Date dateAdded;

    @ManyToOne
    @JoinColumn(name = "space_id", nullable = false)
    @ToString.Exclude
    private Space space;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

}
