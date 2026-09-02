package com.diploma.skillboxjavaspring.repositories;

import com.diploma.skillboxjavaspring.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Provides persistence operations for {@link Hotel} entities.
 */
@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {
}