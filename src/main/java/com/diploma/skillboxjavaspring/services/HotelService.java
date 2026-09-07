package com.diploma.skillboxjavaspring.services;

import com.diploma.skillboxjavaspring.dto.HotelPageResponseDTO;
import com.diploma.skillboxjavaspring.dto.hotels.*;
import com.diploma.skillboxjavaspring.entity.Hotel;
import com.diploma.skillboxjavaspring.exceptions.HotelNotFoundException;
import com.diploma.skillboxjavaspring.exceptions.InvalidHotelRatingException;
import com.diploma.skillboxjavaspring.mapper.HotelMapper;
import com.diploma.skillboxjavaspring.repositories.HotelRepository;
import com.diploma.skillboxjavaspring.specification.HotelSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

/**
 * Provides transactional operations for managing hotels.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HotelService {

    /**
     * Repository used to persist and retrieve hotels.
     */
    private final HotelRepository hotelRepository;

    /**
     * Mapper used to convert between hotel entities and response DTOs.
     */
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
        hotel.setRating(BigDecimal.ZERO);
        hotel.setNumOfRating(0);
        Hotel saved = hotelRepository.save(hotel);

        return hotelMapper.toResponseDTO(saved);
    }


    /**
     * Updates an existing hotel with the values supplied in the request DTO.
     *
     * @param ID              the unique identifier of the hotel to update
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

    /**
     * Updates a hotel's rating and rating count.
     *
     * @param id                    the unique identifier of the hotel to update
     * @param hotelRatingRequestDTO the data containing the new hotel rating
     * @return a response DTO representing the updated hotel
     * @throws HotelNotFoundException      if no hotel exists with the supplied identifier
     * @throws InvalidHotelRatingException if the rating is outside the supported range
     */
    public HotelResponseDTO updateRating(UUID id, HotelRatingRequestDTO hotelRatingRequestDTO) {
        log.debug("=> Update hotel rating by ID. New: {}", hotelRatingRequestDTO.getNewRating());

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));

        BigDecimal newRating = BigDecimal.valueOf(hotelRatingRequestDTO.getNewRating());
        if (newRating.compareTo(BigDecimal.ONE) < 0 ||
                newRating.compareTo(BigDecimal.valueOf(5)) > 0) {
            throw new InvalidHotelRatingException();
        }

        BigDecimal rating = hotel.getRating();
        Integer numberOfRating = hotel.getNumOfRating();
        BigDecimal totalRating;

        if (numberOfRating == 0) {
            rating = newRating;
        } else {
            totalRating = rating.multiply(BigDecimal.valueOf(numberOfRating));
            totalRating = totalRating
                    .subtract(rating)
                    .add(newRating);
            rating = totalRating
                    .divide(
                            BigDecimal.valueOf(numberOfRating),
                            1,
                            RoundingMode.HALF_UP
                    );
        }
        numberOfRating = numberOfRating + 1;

        hotel.setRating(rating);
        hotel.setNumOfRating(numberOfRating);
        Hotel updated = hotelRepository.save(hotel);

        return hotelMapper.toResponseDTO(updated);
    }

    /**
     * Retrieves hotels using filtering and pagination.
     *
     * @param filter   filter parameters
     * @param pageable pagination parameters
     * @return paginated hotel response with total number of matching hotels
     */
    @Transactional(readOnly = true)
    public HotelPageResponseDTO findHotels(HotelFilterDTO filter, Pageable pageable) {
        log.debug("=> Find hotels with filter: {}, pageable: {}", filter, pageable);

        Specification<Hotel> specification = HotelSpecification.filter(filter);
        Page<Hotel> page = hotelRepository
                .findAll(specification, pageable);

        List<HotelResponseDTO> hotels = page
                .getContent()
                .stream()
                .map(hotelMapper::toResponseDTO)
                .toList();

        return new HotelPageResponseDTO(page.getTotalElements(), hotels);
    }
}
