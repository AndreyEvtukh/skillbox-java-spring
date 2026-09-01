package com.diploma.skillboxjavaspring.services;

import com.diploma.skillboxjavaspring.dto.HotelRequestDTO;
import com.diploma.skillboxjavaspring.dto.HotelResponseDTO;
import com.diploma.skillboxjavaspring.entity.Hotel;
import com.diploma.skillboxjavaspring.exeptions.HotelNotFoundException;
import com.diploma.skillboxjavaspring.mapper.HotelMapper;
import com.diploma.skillboxjavaspring.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Provides transactional operations for managing hotels.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HotelService {
    private final HotelRepository hotelRepository;

    private final HotelMapper hotelMapper;

    /**
     * Retrieves all hotels.
     *
     * @return response DTOs representing all stored hotels
     */
    @Transactional(readOnly = true)
    public List<HotelResponseDTO> getAll() {
        log.info("=> Get all hotels");

        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toResponseDTO)
                .toList();
    }


    /**
     * Retrieves a hotel by its unique identifier.
     *
     * @param ID the unique identifier of the hotel
     * @return a response DTO representing the requested hotel
     * @throws HotelNotFoundException if no hotel exists with the specified identifier
     */
    @Transactional(readOnly = true)
    public HotelResponseDTO getById(UUID ID) {
        log.debug("=> Find hotel by ID {}", ID);

        Hotel hotel = hotelRepository.findById(ID)
                .orElseThrow(() -> new HotelNotFoundException(ID));

        return hotelMapper.toResponseDTO(hotel);
    }

    /**
     * Creates a new hotel.
     *
     * @param hotelRequestDTO the data for the hotel to create
     * @return a response DTO representing the persisted hotel
     */
    @Transactional
    public HotelResponseDTO create(HotelRequestDTO hotelRequestDTO) {
        log.debug("=> Add new hotel {}", hotelRequestDTO);

        Hotel hotel = hotelMapper.toEntity(hotelRequestDTO);
        hotel.setRating(BigDecimal.ONE);
        hotel.setRatingCount(0);
        Hotel saved = hotelRepository.save(hotel);

        return hotelMapper.toResponseDTO(saved);
    }


    /**
     * Updates an existing hotel with the values supplied in the request DTO.
     *
     * @param ID the unique identifier of the hotel to update
     * @param hotelRequestDTO the data containing the hotel's updated values
     * @return a response DTO representing the updated hotel
     * @throws HotelNotFoundException if no hotel exists with the supplied identifier
     */
    @Transactional
    public HotelResponseDTO update(UUID ID, HotelRequestDTO hotelRequestDTO) {
        log.debug("=> Update hotel {}", hotelRequestDTO);

        Hotel hotel = hotelRepository.findById(ID)
                .orElseThrow(() -> new HotelNotFoundException(ID));

        hotelMapper.updateEntity(hotelRequestDTO, hotel);
        Hotel updated = hotelRepository.save(hotel);

        return hotelMapper.toResponseDTO(updated);
    }

    /**
     * Deletes a hotel by its identifier.
     *
     * @param ID the unique identifier of the hotel to delete
     * @throws HotelNotFoundException if no hotel exists with the specified identifier
     */
    @Transactional
    public void deleteById(UUID ID) {
        log.debug("=> Delete hotel by ID {}", ID);

        Hotel hotel = hotelRepository.findById(ID)
                .orElseThrow(() -> new HotelNotFoundException(ID));

        hotelRepository.delete(hotel);
    }
}
