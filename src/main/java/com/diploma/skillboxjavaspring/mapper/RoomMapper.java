package com.diploma.skillboxjavaspring.mapper;

import com.diploma.skillboxjavaspring.dto.BookingPeriodDTO;
import com.diploma.skillboxjavaspring.dto.RoomRequestDTO;
import com.diploma.skillboxjavaspring.dto.RoomResponseDTO;
import com.diploma.skillboxjavaspring.dto.RoomUpdateDTO;
import com.diploma.skillboxjavaspring.entity.Booking;
import com.diploma.skillboxjavaspring.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * Converts between {@link Room} entities and room API DTOs.
 *
 * <p>Room-to-entity mappings do not set the entity identifier or hotel association;
 * those values are managed by the persistence layer and service layer respectively.</p>
 */
@Mapper(componentModel = SPRING)
public interface RoomMapper {

    /**
     * Creates a new room entity from the supplied request DTO.
     *
     * <p>The entity identifier and hotel association are not mapped. The service layer
     * resolves the request's hotel identifier and assigns the associated hotel.</p>
     *
     * @param dto the room data received in an API request
     * @return a new room entity populated with the request data
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    Room toEntity(RoomRequestDTO dto);


    @Mapping(target = "hotelId", source = "hotel.id")
    @Mapping(target = "bookingPeriods", source = "bookings")
    RoomResponseDTO toResponseDTO(Room room);

    default List<BookingPeriodDTO> mapBookingPeriods(List<Booking> bookings) {

        if (bookings == null) {
            return List.of();
        }

        return bookings.stream()
                .map(booking -> new BookingPeriodDTO(
                        booking.getCheckIn(),
                        booking.getCheckOut().minusDays(1)
                ))
                .toList();
    }

    /**
     * Updates an existing room entity using values from an update DTO.
     *
     * <p>The entity identifier and hotel association remain unchanged.</p>
     *
     * @param dto the DTO containing the updated room data
     * @param room the existing room entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    void updateEntity(RoomUpdateDTO dto, @MappingTarget Room room);
}
