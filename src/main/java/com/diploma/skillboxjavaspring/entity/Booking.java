package com.diploma.skillboxjavaspring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Persistent reservation of a room by a user for a defined date range.
 *
 * <p>The check-out date is the first day after the stay. Both the room and user
 * associations are required and loaded lazily.</p>
 */
@Setter
@Getter
@Entity
@Table(name = "booking")
public class Booking {

    /**
     * Database-generated unique identifier of this booking.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /**
     * First day of the reserved stay.
     */
    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    /**
     * First day after the reserved stay.
     */
    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    /**
     * Room reserved by this booking.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    /**
     * User who created this booking.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
