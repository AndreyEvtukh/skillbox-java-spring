package com.diploma.skillboxjavaspring.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Persistent representation of a hotel.
 *
 * <p>Each instance corresponds to a row in the {@code hotels} table. Its rating
 * data is maintained separately from the hotel details submitted by clients.</p>
 */
@Entity
@Data
@Table(name = "hotels")
public class Hotel {

    /**
     * Unique identifier of the hotel, generated when the entity is persisted.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /**
     * Hotel name.
     */
    @Column(name = "name")
    private String name;

    /**
     * Short descriptive title of the hotel.
     */
    @Column(name = "title")
    private String title;

    /**
     * City in which the hotel is located.
     */
    @Column(name = "city")
    private String city;

    /**
     * Street address of the hotel.
     */
    @Column(name = "address")
    private String address;

    /**
     * Distance to the relevant reference location.
     */
    @Column(name = "distance")
    private BigDecimal distance;

    /**
     * Current average rating, initialized to {@link BigDecimal#ONE}.
     */
    @Column(name = "rating")
    private BigDecimal rating = BigDecimal.ONE;

    /**
     * Number of ratings included in the current average, initialized to zero.
     */
    @Column(name = "rating_count")
    private Integer ratingCount = 0;

    /**
     * Rooms belonging to this hotel.
     */
    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Room> rooms;
}
