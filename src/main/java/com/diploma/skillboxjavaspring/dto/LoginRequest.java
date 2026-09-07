package com.diploma.skillboxjavaspring.dto;

public record LoginRequest(
        String email,
        String password
) {
}