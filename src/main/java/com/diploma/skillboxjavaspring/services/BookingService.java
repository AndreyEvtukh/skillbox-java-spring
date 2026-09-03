package com.diploma.skillboxjavaspring.services;

import com.diploma.skillboxjavaspring.dto.BookingRequestDTO;
import com.diploma.skillboxjavaspring.dto.BookingResponseDTO;
import com.diploma.skillboxjavaspring.entity.Booking;
import com.diploma.skillboxjavaspring.entity.Room;
import com.diploma.skillboxjavaspring.entity.User;
import com.diploma.skillboxjavaspring.exceptions.RoomAlreadyBookedException;
import com.diploma.skillboxjavaspring.mapper.BookingMapper;
import com.diploma.skillboxjavaspring.repositories.BookingRepository;
import com.diploma.skillboxjavaspring.repositories.RoomRepository;
import com.diploma.skillboxjavaspring.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Provides transactional operations for retrieving and creating room bookings.
 *
 * <p>When creating a booking, this service verifies that the referenced user and room
 * exist, validates the requested date range, and prevents overlapping stays for a room.</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    private final BookingMapper bookingMapper;

    /**
     * Retrieves all bookings.
     *
     * @return booking data for every persisted booking
     */
    @Transactional(readOnly = true)
    public List<BookingResponseDTO> getAll() {
        log.info("=> Get all booked rooms");

        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toDTO)
                .toList();
    }

    /**
     * Creates a booking for an available room.
     *
     * @param dto the user, room, and requested check-in and check-out dates
     * @return data for the persisted booking
     * @throws EntityNotFoundException if the requested room or user does not exist
     * @throws IllegalArgumentException if the check-out date is not after the check-in date
     * @throws RoomAlreadyBookedException if the room already has an overlapping booking
     */
    @Transactional
    public BookingResponseDTO create(BookingRequestDTO dto) {
        log.debug("=> Add new booking {}", dto);

        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + dto.getRoomId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + dto.getUserId()));

        if (!dto.getCheckIn().isBefore(dto.getCheckOut())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        Optional<Booking> conflictingBooking =
                bookingRepository
                        .findFirstByRoomIdAndCheckInLessThanAndCheckOutGreaterThan(
                                dto.getRoomId(),
                                dto.getCheckOut(),
                                dto.getCheckIn()
                        );

        if (conflictingBooking.isPresent()) {
            Booking existingBooking = conflictingBooking.get();

            throw new RoomAlreadyBookedException(
                    "Room is already booked from "
                            + existingBooking.getCheckIn()
                            + " to "
                            + existingBooking.getCheckOut().minusDays(1)
            );
        }

        Booking booking = new Booking();

        booking.setCheckIn(dto.getCheckIn());
        booking.setCheckOut(dto.getCheckOut());
        booking.setRoom(room);
        booking.setUser(user);

        Booking saved = bookingRepository.save(booking);

        log.debug("<= New booking {}", saved);

        return bookingMapper.toDTO(saved);
    }
}
