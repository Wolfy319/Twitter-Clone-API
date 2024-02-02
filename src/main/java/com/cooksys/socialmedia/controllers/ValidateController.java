package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for validation-related operations.
 */
@RestController
@RequestMapping("/validate")
@RequiredArgsConstructor
public class ValidateController {

    private final ValidateService validateService;

    /**
     * Checks whether a hashtag with the given label exists.
     *
     * @param label the label of the hashtag
     * @return Response containing true if the hashtag exists, false otherwise
     */
    @GetMapping("/tag/exists/{label}")
    public Boolean tagExists(@PathVariable String label) {
        return validateService.tagExists(label);
    }

    /**
     * Checks whether a username exists in the system.
     *
     * @param username the username to check
     * @return Response containing true if the username exists, false otherwise
     */
    @GetMapping("/username/exists/@{username}")
    public Boolean usernameExists(@PathVariable String username) {
        return validateService.usernameExists(username);
    }

    /**
     * Checks whether a username is available.
     *
     * @param username the username to check
     * @return Response containing true if the username is available, false otherwise
     */
    @GetMapping("/username/available/@{username}")
    public Boolean usernameAvailable(@PathVariable String username) {
        return validateService.usernameAvailable(username);
    }

}
