package com.cooksys.socialmedia.services;

/**
 * Service interface for validation-related operations.
 */
public interface ValidateService {

    /**
     * Checks if a hashtag with the specified label exists.
     *
     * @param label the label of the hashtag
     * @return true if the hashtag exists, false otherwise
     */
    boolean tagExists(String label);

    /**
     * Checks if a username exists in the system.
     *
     * @param username the username to check
     * @return true if the username exists, false otherwise
     */
    boolean usernameExists(String username);

    /**
     * Checks if a username is available.
     *
     * @param username the username to check
     * @return true if the username is available, false otherwise
     */
    boolean usernameAvailable(String username);
}
