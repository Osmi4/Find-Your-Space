package com.example.backend.entity;

import com.example.backend.enums.Availibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
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

    @Column(name = "availibility", nullable = false)
    @Enumerated(EnumType.STRING)
    private Availibility availibility;

    //we will keep image on server and store the path in database
    @Column(name = "space_image", nullable = false)
    private String spaceImage;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

}
