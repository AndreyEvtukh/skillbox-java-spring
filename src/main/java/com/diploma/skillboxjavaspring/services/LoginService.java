package com.diploma.skillboxjavaspring.services;

import com.diploma.skillboxjavaspring.dto.*;
import com.diploma.skillboxjavaspring.entity.User;
import com.diploma.skillboxjavaspring.exceptions.InvalidLoginEmailException;
import com.diploma.skillboxjavaspring.exceptions.InvalidLoginPasswordException;
import com.diploma.skillboxjavaspring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Transactional()
    public LoginResponse login(LoginRequest request) {

        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new InvalidLoginEmailException(request.email()));

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash()
        )) {
            throw new InvalidLoginPasswordException();
        }
        log.info("=> updateStatus 1");
        userService.updateStatus(user.getId(), true);
        return new LoginResponse(user.getUsername(), user.getEmail(), user.getRole(), user.getActive());
    }

    @Transactional()
    public LogoutResponse logout(LogoutRequest request) {

        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new InvalidLoginEmailException(request.email()));

        userService.updateStatus(user.getId(), false);
        return new LogoutResponse(user.getUsername(), true, user.getActive());
    }

    @Transactional(readOnly = true)
    public CheckActiveResponse checkStatus(CheckActiveRequest request) {

        User user = userRepository
                .findByEmail(request.email())
                .orElseThrow(() -> new InvalidLoginEmailException(request.email()));
        log.debug("=> {}", user.getEmail());
        return new CheckActiveResponse(user.getEmail(), user.getActive(), user.getRole(), true);
    }
}