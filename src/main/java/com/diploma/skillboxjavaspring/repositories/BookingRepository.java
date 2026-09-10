package com.diploma.skillboxjavaspring.repositories;

import com.diploma.skillboxjavaspring.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for managing {@link Booking} entities and querying room booking availability.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    /**
     * Finds the first booking for the specified room that overlaps the given date range.
     * The booking is considered overlapping when its check-in date is before the requested
     * check-out date and its check-out date is after the requested check-in date.
     *
     * @param roomId identifier of the room to inspect
     * @param checkOut the requested end date, exclusive for overlap checks
     * @param checkIn the requested start date, inclusive for overlap checks
     * @return an optional booking that overlaps the requested range, if one exists
     */
    Optional<Booking> findFirstByRoomIdAndCheckInLessThanAndCheckOutGreaterThan(
            UUID roomId,
            LocalDate checkOut,
            LocalDate checkIn
    );
}
