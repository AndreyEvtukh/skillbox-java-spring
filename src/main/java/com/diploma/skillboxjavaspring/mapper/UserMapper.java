package com.diploma.skillboxjavaspring.mapper;

import com.diploma.skillboxjavaspring.dto.*;
import com.diploma.skillboxjavaspring.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * Maps between user entities and their request and response DTOs.
 */
@Mapper(componentModel = SPRING)
public interface UserMapper {

    /**
     * Converts user creation data into a user entity.
     *
     * @param dto the user creation data
     * @return a user entity populated from the DTO
     */
    User toEntity(UserRequestDTO dto);

    /**
     * Converts a user entity into response data.
     *
     * @param user the user entity to convert
     * @return the corresponding response DTO
     */
    UserResponseDTO toResponseDTO(User user);

    /**
     * Updates a user entity with the supplied update data, preserving its password hash.
     *
     * @param dto the user update data
     * @param user the entity to update
     */
    @Mapping(target = "passwordHash", ignore = true)
    void updateEntity(UserUpdateDTO dto, @MappingTarget User user);
}
