package com.diploma.skillboxjavaspring.dto;

public record LogoutResponse(
        String email,
        Boolean ok,
        Boolean active
) {}
