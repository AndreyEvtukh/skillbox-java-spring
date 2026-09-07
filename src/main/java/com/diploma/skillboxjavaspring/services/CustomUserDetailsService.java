package com.diploma.skillboxjavaspring.services;

import com.diploma.skillboxjavaspring.entity.User;
import com.diploma.skillboxjavaspring.repositories.UserRepository;
import com.diploma.skillboxjavaspring.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Loads user details for Spring Security authentication.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads a user by email address.
     *
     * @param email the email address used to find the user
     * @return the user's security details
     * @throws UsernameNotFoundException if no user has the specified email address
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + email
                        )
                );

        return new CustomUserDetails(user);
    }
}