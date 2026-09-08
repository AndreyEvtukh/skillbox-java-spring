package com.diploma.skillboxjavaspring.controllers;

import com.diploma.skillboxjavaspring.dto.*;
import com.diploma.skillboxjavaspring.dto.login.LoginRequest;
import com.diploma.skillboxjavaspring.dto.login.LoginResponse;
import com.diploma.skillboxjavaspring.dto.logout.LogoutRequest;
import com.diploma.skillboxjavaspring.dto.logout.LogoutResponse;
import com.diploma.skillboxjavaspring.exceptions.InvalidLoginEmailException;
import com.diploma.skillboxjavaspring.exceptions.InvalidLoginPasswordException;
import com.diploma.skillboxjavaspring.services.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Exposes REST endpoints for user authentication and session status.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    /**
     * Service that implements authentication operations.
     */
    private final LoginService loginService;

    /**
     * Authenticates a user with the supplied credentials.
     *
     * @param request the login credentials
     * @return an HTTP 200 response containing the authenticated user's data
     * @throws InvalidLoginEmailException if no user has the specified email address
     * @throws InvalidLoginPasswordException if the password is incorrect
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        log.info("=> login {}",  request);
        LoginResponse result = loginService.login(request);
        return ResponseEntity.ok(result);
    }

    /**
     * Logs out a user identified by the supplied email address.
     *
     * @param request the logout request
     * @return an HTTP 200 response containing the logout result
     * @throws InvalidLoginEmailException if no user has the specified email address
     */
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(
            @RequestBody LogoutRequest request
    ) {
        LogoutResponse result = loginService.logout(request);
        return ResponseEntity.ok(result);
    }

    /**
     * Checks whether a user is currently active.
     *
     * @param request the request containing the user's email address
     * @return an HTTP 200 response containing the user's active status
     * @throws InvalidLoginEmailException if no user has the specified email address
     */
    @PostMapping("/me")
    public ResponseEntity<CheckActiveResponse> checkActive(
            @RequestBody CheckActiveRequest request
    ) {
        CheckActiveResponse result = loginService.checkStatus(request);
        return ResponseEntity.ok(result);
    }
}