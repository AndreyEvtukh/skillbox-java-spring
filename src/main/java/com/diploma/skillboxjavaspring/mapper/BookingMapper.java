package com.diploma.skillboxjavaspring.mapper;

import com.diploma.skillboxjavaspring.dto.BookingRequestDTO;
import com.diploma.skillboxjavaspring.dto.BookingResponseDTO;
import com.diploma.skillboxjavaspring.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * Maps between booking DTOs and the {@link Booking} entity.
 */
@Mapper(componentModel = SPRING)
public interface BookingMapper {

    /**
     * Converts a booking creation request into a persistent booking entity.
     *
     * @param dto the booking request data
     * @return the mapped booking entity
     */
    Booking toEntity(BookingRequestDTO dto);

    /**
     * Converts a booking entity into a response DTO, including related user and room identifiers.
     *
     * @param booking the booking entity to convert
     * @return the booking response DTO
     */
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "room.id", target = "roomId")
    BookingResponseDTO toDTO(Booking booking);
}
