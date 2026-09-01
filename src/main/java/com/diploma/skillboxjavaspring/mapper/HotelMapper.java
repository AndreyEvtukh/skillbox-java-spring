package com.diploma.skillboxjavaspring.mapper;

import com.diploma.skillboxjavaspring.dto.HotelRequestDTO;
import com.diploma.skillboxjavaspring.dto.HotelResponseDTO;
import com.diploma.skillboxjavaspring.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * Converts between {@link Hotel} entities and hotel API DTOs.
 *
 * <p>Mappings from {@link HotelRequestDTO} intentionally preserve the entity's
 * identifier and rating data, because those values are managed by the persistence
 * layer and rating operations.</p>
 */
@Mapper(componentModel = SPRING)
public interface HotelMapper {

    /**
     * Creates a new hotel entity from the supplied request DTO.
     * The entity identifier, rating, and rating count are not mapped.
     *
     * @param dto the hotel data received in an API request
     * @return a new hotel entity populated with the request data and without rating data
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    Hotel toEntity(HotelRequestDTO dto);

    /**
     * Converts the supplied hotel entity to its API response DTO.
     *
     * @param hotel the hotel entity to convert
     * @return a response DTO containing the hotel's data
     */
    HotelResponseDTO toResponseDTO(Hotel hotel);

    /**
     * Updates an existing hotel entity with values from a request DTO.
     * The entity identifier, rating, and rating count remain unchanged.
     *
     * @param dto the request DTO containing the updated hotel data
     * @param hotel the existing hotel entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    void updateEntity(HotelRequestDTO dto, @MappingTarget Hotel hotel);
}
