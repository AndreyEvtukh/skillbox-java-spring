package com.diploma.skillboxjavaspring.dto;

import com.diploma.skillboxjavaspring.entity.Role;

public record CheckActiveResponse(
        String email,
        Boolean active,
        Role role,
        Boolean ok
) {
}