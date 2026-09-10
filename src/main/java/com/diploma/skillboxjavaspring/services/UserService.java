package com.diploma.skillboxjavaspring.services;

import com.diploma.skillboxjavaspring.dto.user.UserRequestDTO;
import com.diploma.skillboxjavaspring.dto.user.UserResponseDTO;
import com.diploma.skillboxjavaspring.entity.Role;
import com.diploma.skillboxjavaspring.entity.User;
import com.diploma.skillboxjavaspring.exceptions.UserEmailExistedException;
import com.diploma.skillboxjavaspring.exceptions.UserIDNotFoundException;
import com.diploma.skillboxjavaspring.exceptions.UserNameExistedException;
import com.diploma.skillboxjavaspring.exceptions.UserNameNotFoundException;
import com.diploma.skillboxjavaspring.mapper.UserMapper;
import com.diploma.skillboxjavaspring.repositories.UserRepository;
import com.diploma.skillboxjavaspring.statistics.dto.UserRegisteredEvent;
import com.diploma.skillboxjavaspring.statistics.service.StatisticsEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Implements user retrieval, creation, update, and deletion operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final StatisticsEventPublisher statisticsEventPublisher;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves all users.
     *
     * @return the list of all user data
     */
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAll() {
        log.debug("=> Get all users");

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

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
     * @throws UserNameExistedException  if the username is already in use
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

        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setRole(Role.USER);
        user.setActive(false);
        User saved = userRepository.save(user);

        statisticsEventPublisher.publishUserRegistered(
                UserRegisteredEvent.create(saved.getId())
        );

        log.debug("<= Saved user {}", saved);

        return userMapper.toResponseDTO(saved);
    }

    /**
     * Updates an existing user.
     *
     * @param id            the unique ID of the user to update
     * @param request the updated user data
     * @return the updated user data
     * @throws UserIDNotFoundException   if no user has the specified ID
     * @throws UserNameExistedException  if the username is already in use
     * @throws UserEmailExistedException if the email address is already in use
     */
    @Transactional
    public UserResponseDTO update(UUID id, UserRequestDTO request) {
        log.debug("=> Update user {}", request);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserIDNotFoundException(id));

        userMapper.updateEntity(request, user);
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );
        User updated = userRepository.save(user);
        UserResponseDTO response = userMapper.toResponseDTO(updated);
        response.setEvent("Edit");

        log.debug("<= Updated user {}", response);

        return response;
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

    /**
     * Updates a user's active status.
     *
     * @param id     the unique ID of the user to update
     * @param active the new active status
     * @return the updated user data
     * @throws UserIDNotFoundException if no user has the specified ID
     */
    @Transactional
    public UserResponseDTO updateStatus(UUID id, Boolean active) {
        log.debug("=> Set user status by ID {}: {}", id, active);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserIDNotFoundException(id));
        user.setActive(active);
        userRepository.flush();

        return userMapper.toResponseDTO(user);
    }
}
