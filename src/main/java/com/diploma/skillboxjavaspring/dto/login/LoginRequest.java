package com.diploma.skillboxjavaspring.dto.login;

public record LoginRequest(
        String email,
        String password
) {
}