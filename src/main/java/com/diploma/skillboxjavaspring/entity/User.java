package com.diploma.skillboxjavaspring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * Represents an application user with authentication data, role information,
 * and the bookings associated with the account.
 */
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    /**
     * Unique user identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /**
     * User email address used for authentication and contact.
     */
    @Column(name = "email")
    private String email;

    /**
     * Unique username visible in the application.
     */
    @Column(name = "username")
    private String username;

    /**
     * Hashed password used to authenticate the user.
     */
    @Column(name = "password_hash")
    private String passwordHash;

    /**
     * User role in the system, such as admin or regular user.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    /**
     * Bookings created by this user.
     */
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;
}