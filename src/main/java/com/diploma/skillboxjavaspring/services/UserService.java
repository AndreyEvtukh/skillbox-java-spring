package com.diploma.skillboxjavaspring.services;

import com.diploma.skillboxjavaspring.dto.UserRequestDTO;
import com.diploma.skillboxjavaspring.dto.UserResponseDTO;
import com.diploma.skillboxjavaspring.dto.UserUpdateDTO;
import com.diploma.skillboxjavaspring.entity.User;
import com.diploma.skillboxjavaspring.exceptions.UserEmailExistedException;
import com.diploma.skillboxjavaspring.exceptions.UserIDNotFoundException;
import com.diploma.skillboxjavaspring.exceptions.UserNameExistedException;
import com.diploma.skillboxjavaspring.exceptions.UserNameNotFoundException;
import com.diploma.skillboxjavaspring.mapper.UserMapper;
import com.diploma.skillboxjavaspring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implements user retrieval, creation, update, and deletion operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Retrieves a user by username.
     *
     * @param username the user's username
     * @return the matching user data
     * @throws UserNameNotFoundException if no user has the specified username
     */
    @Transactional(readOnly = true)
    public UserResponseDTO getByUsername(String username) {
        log.debug("=> Find user by name {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNameNotFoundException(username));

        return userMapper.toResponseDTO(user);
    }

    /**
     * Creates and persists a new user.
     *
     * @param userRequestDTO the data for the user to create
     * @return the persisted user data
     * @throws UserNameExistedException if the username is already in use
     * @throws UserEmailExistedException if the email address is already in use
     */
    @Transactional
    public UserResponseDTO create(UserRequestDTO userRequestDTO) {
        log.debug("=> Add new user {}", userRequestDTO);

        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new UserNameExistedException(userRequestDTO.getUsername());
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new UserEmailExistedException(userRequestDTO.getEmail());
        }

        User user = userMapper.toEntity(userRequestDTO);
        User saved = userRepository.save(user);

        log.debug("<= Saved user {}", saved);

        return userMapper.toResponseDTO(saved);
    }

    /**
     * Updates an existing user.
     *
     * @param id the unique ID of the user to update
     * @param userUpdateDTO the updated user data
     * @return the updated user data
     * @throws UserIDNotFoundException if no user has the specified ID
     * @throws UserNameExistedException if the username is already in use
     * @throws UserEmailExistedException if the email address is already in use
     */
    @Transactional
    public UserResponseDTO update(UUID id, UserUpdateDTO userUpdateDTO) {
        log.debug("=> Update user {}", userUpdateDTO);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserIDNotFoundException(id));

        if (userRepository.existsByUsername(userUpdateDTO.getUsername())) {
            throw new UserNameExistedException(userUpdateDTO.getUsername());
        }

        if (userRepository.existsByEmail(userUpdateDTO.getEmail())) {
            throw new UserEmailExistedException(userUpdateDTO.getEmail());
        }

        userMapper.updateEntity(userUpdateDTO, user);

        User updated = userRepository.save(user);

        log.debug("<= Updated user {}", updated);

        return userMapper.toResponseDTO(updated);
    }

    /**
     * Deletes a user by unique ID.
     *
     * @param id the unique ID of the user to delete
     * @throws UserIDNotFoundException if no user has the specified ID
     */
    @Transactional
    public void delete(UUID id) {
        log.debug("=> Delete user by ID {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserIDNotFoundException(id));

        userRepository.delete(user);
    }
}
