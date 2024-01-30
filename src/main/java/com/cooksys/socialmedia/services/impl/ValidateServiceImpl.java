package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.ValidateService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of the ValidateService.
 * Provides methods to validate various aspects such as existence of hashtags and usernames.
 */
@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;

    /**
     * Checks if a hashtag with the specified label exists in the database.
     *
     * @param label the label of the hashtag to check
     * @return true if the hashtag exists, false otherwise
     */
    @Override
    public boolean tagExists(String label) {
        return hashtagRepository.existsByLabel(label);
    }

    /**
     * Checks if a username exists in the system.
     *
     * @param username the username to check
     * @return true if the username exists, false otherwise
     */
    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByCredentialsUsername(username);
    }

    /**
     * Checks if a username is available, meaning it does not exist in the system.
     *
     * @param username the username to check
     * @return true if the username is available (does not exist), false if it exists
     */
    @Override
    public boolean usernameAvailable(String username) {
        return !userRepository.existsByCredentialsUsername(username);
    }
}
