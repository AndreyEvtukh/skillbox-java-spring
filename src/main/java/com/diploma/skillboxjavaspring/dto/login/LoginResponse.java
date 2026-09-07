package com.diploma.skillboxjavaspring.dto.login;

import com.diploma.skillboxjavaspring.entity.Role;

public record LoginResponse(
        String username,
        String email,
        Role role,
        Boolean active
) {}
