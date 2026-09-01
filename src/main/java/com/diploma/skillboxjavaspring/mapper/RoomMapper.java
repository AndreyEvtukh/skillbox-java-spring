package com.diploma.skillboxjavaspring.mapper;

import com.diploma.skillboxjavaspring.dto.RoomRequestDTO;
import com.diploma.skillboxjavaspring.dto.RoomResponseDTO;
import com.diploma.skillboxjavaspring.dto.RoomUpdateDTO;
import com.diploma.skillboxjavaspring.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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

    /**
     * Converts the supplied room entity to its API response DTO.
     *
     * <p>The identifier of the associated hotel is mapped to {@code hotelId}.</p>
     *
     * @param room the room entity to convert
     * @return a response DTO containing the room data and associated hotel identifier
     */
    @Mapping(target = "hotelId", source = "hotel.id")
    RoomResponseDTO toResponseDTO(Room room);

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
