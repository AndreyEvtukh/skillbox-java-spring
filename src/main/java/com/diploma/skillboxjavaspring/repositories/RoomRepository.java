package com.diploma.skillboxjavaspring.repositories;

import com.diploma.skillboxjavaspring.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Provides persistence operations for {@link Room} entities.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
}