package com.cooksys.socialmedia.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for user credentials, containing username and password.
 * Used primarily for authentication purposes.
 * Note: Password handling in this DTO is for demonstration. Always handle passwords securely in production.
 */
@Data
@NoArgsConstructor
public class CredentialsDto {

    /**
     * Unique identifier for a user.
     */
    private String username;

    /**
     * User's password. Used for login authentication.
     * Note: Represented as plain text here for simplicity, not a secure practice.
     */
    private String password;
}
