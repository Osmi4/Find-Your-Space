package com.example.backend.entity;

import com.example.backend.enums.Availibility;
import com.example.backend.enums.SpaceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "space", uniqueConstraints = {@UniqueConstraint(columnNames = "spaceId")})
public class Space {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String spaceId;

    @Column(name = "spaceName", nullable = false)
    private String spaceName;

    @Column(name = "space_location", nullable = false)
    private String spaceLocation;

    @Column(name = "space_size", nullable = false)
    private double spaceSize;

    @Column(name = "space_price", nullable = false)
    private double spacePrice;

    @Column(name = "space_description", nullable = true)
    private String spaceDescription;

    @Column(name = "space_type", nullable = false)
    private SpaceType spaceType;

    @Column(name = "date_added", nullable = false)
    private Date dateAdded;

    @Column(name = "date_updated", nullable = false)
    private Date dateUpdated;

    @Column(name = "availability", nullable = false)
    @Enumerated(EnumType.STRING)
    private Availibility availability;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Image> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @ToString.Exclude
    private User owner;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Booking> bookings = new ArrayList<>();

}
