package com.diploma.skillboxjavaspring.security;

import com.diploma.skillboxjavaspring.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Adapts the application user entity to Spring Security's user-details API.
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    /**
     * Returns the user's role as a Spring Security authority.
     *
     * @return the user's granted authorities
     */
    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
    }

    /**
     * Returns the stored password hash.
     *
     * @return the user's password hash
     */
    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    /**
     * Uses the user's email address as the security username.
     *
     * @return the user's email address
     */
    @Override
    public @NonNull String getUsername() {
        return user.getEmail();
    }

}