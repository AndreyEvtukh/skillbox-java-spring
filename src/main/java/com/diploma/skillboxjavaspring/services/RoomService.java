package com.diploma.skillboxjavaspring.services;

import com.diploma.skillboxjavaspring.dto.RoomRequestDTO;
import com.diploma.skillboxjavaspring.dto.RoomResponseDTO;
import com.diploma.skillboxjavaspring.dto.RoomUpdateDTO;
import com.diploma.skillboxjavaspring.entity.Hotel;
import com.diploma.skillboxjavaspring.entity.Room;
import com.diploma.skillboxjavaspring.exeptions.HotelNotFoundException;
import com.diploma.skillboxjavaspring.exeptions.RoomNotFoundException;
import com.diploma.skillboxjavaspring.mapper.RoomMapper;
import com.diploma.skillboxjavaspring.repositories.HotelRepository;
import com.diploma.skillboxjavaspring.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Provides transactional operations for managing rooms.
 *
 * <p>The service resolves hotel references during room creation and converts
 * persisted room entities to API response DTOs.</p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    /**
     * Repository used to access room entities.
     */
    private final RoomRepository roomRepository;

    /**
     * Repository used to resolve the hotel assigned to a new room.
     */
    private final HotelRepository hotelRepository;

    /**
     * Mapper used to convert between room entities and DTOs.
     */
    private final RoomMapper roomMapper;

    /**
     * Retrieves a room by its identifier.
     *
     * @param ID the unique identifier of the room
     * @return the response DTO representing the requested room
     * @throws RoomNotFoundException if no room exists with the supplied identifier
     */
    @Transactional(readOnly = true)
    public RoomResponseDTO getById(UUID ID) {
        log.warn("=> Find room by ID {}", ID);

        Room room = roomRepository.findById(ID)
                .orElseThrow(() -> new RoomNotFoundException(ID));

        return roomMapper.toResponseDTO(room);
    }

    /**
     * Creates and persists a new room.
     *
     * <p>The hotel identified by the request is resolved and assigned to the new
     * room before it is saved.</p>
     *
     * @param roomRequestDTO the data for the room to create
     * @return the response DTO representing the created room
     * @throws HotelNotFoundException if the request's hotel identifier does not exist
     */
    @Transactional
    public RoomResponseDTO create(RoomRequestDTO roomRequestDTO) {
        log.warn("=> Add new room {}", roomRequestDTO);

        Hotel hotel = hotelRepository.findById(roomRequestDTO.getHotelId())
                .orElseThrow(() ->
                        new HotelNotFoundException(roomRequestDTO.getHotelId()));


        Room room = roomMapper.toEntity(roomRequestDTO);
        room.setHotel(hotel);
        Room saved = roomRepository.save(room);

        log.warn("=> Saved room {}", saved);

        return roomMapper.toResponseDTO(saved);
    }

    /**
     * Updates an existing room without changing its associated hotel.
     *
     * @param ID the unique identifier of the room to update
     * @param roomUpdateDTO the replacement data for the room
     * @return the response DTO representing the updated room
     * @throws RoomNotFoundException if no room exists with the supplied identifier
     */
    @Transactional
    public RoomResponseDTO update(UUID ID, RoomUpdateDTO roomUpdateDTO) {
        log.warn("=> Update room {}", roomUpdateDTO);

        Room room = roomRepository.findById(ID)
                .orElseThrow(() -> new RoomNotFoundException(ID));


        roomMapper.updateEntity(roomUpdateDTO, room);
        Room updated = roomRepository.save(room);

        log.warn("=> Updated room {}", updated);

        return roomMapper.toResponseDTO(updated);
    }

    /**
     * Deletes the room identified by the supplied identifier.
     *
     * @param ID the unique identifier of the room to delete
     * @throws RoomNotFoundException if no room exists with the supplied identifier
     */
    @Transactional
    public void deleteById(UUID ID) {
        log.warn("=> Delete room by ID {}", ID);

        Room room = roomRepository.findById(ID)
                .orElseThrow(() -> new RoomNotFoundException(ID));

        roomRepository.delete(room);
    }
}
