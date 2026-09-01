package com.diploma.skillboxjavaspring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(
            name = "room_closed_dates",
            joinColumns = @JoinColumn(name = "room_id")
    )
    @Column(name = "closed_date")
    private List<LocalDate> closedDates;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="hotel_id", nullable = false)
    private Hotel hotel;
}
