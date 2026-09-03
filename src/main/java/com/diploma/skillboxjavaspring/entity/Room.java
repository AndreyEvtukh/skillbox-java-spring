package com.diploma.skillboxjavaspring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Represents a room belonging to a hotel.
 */
@Entity
@Getter
@Setter
@Table(name = "rooms")
public class Room {

    /**
     * The room's unique identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /**
     * The room's name.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The room number within the hotel.
     */
    @Column(name = "number", nullable = false)
    private Integer number;

    /**
     * The room's nightly price.
     */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /**
     * The maximum number of guests the room accommodates.
     */
    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    /**
     * A description of the room and its amenities.
     */
    @Column(name = "description")
    private String description;

    /**
     * The hotel to which this room belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="hotel_id", nullable = false)
    private Hotel hotel;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;
}
