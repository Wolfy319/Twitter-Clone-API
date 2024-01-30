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
     * @return true if the hashtag exists, false otherwise
     */
    @GetMapping("/tag/exists/{label}")
    public ResponseEntity<Boolean> tagExists(@PathVariable String label) {
        return ResponseEntity.ok(validateService.tagExists(label));
    }

    //TODO: Uncomment and test when existByUsername() in UserRepository.java is implemented
//    /**
//     * Checks whether a username exists in the system.
//     *
//     * @param username the username to check
//     * @return true if the username exists, false otherwise
//     */
//    @GetMapping("/username/exists/@{username}")
//    public ResponseEntity<Boolean> usernameExists(@PathVariable String username) {
//        return ResponseEntity.ok(validateService.usernameExists(username));
//    }
//
//    /**
//     * Checks whether a username is available.
//     *
//     * @param username the username to check
//     * @return true if the username is available, false otherwise
//     */
//    @GetMapping("/username/available/@{username}")
//    public ResponseEntity<Boolean> usernameAvailable(@PathVariable String username) {
//        return ResponseEntity.ok(validateService.usernameAvailable(username));
//    }
}
