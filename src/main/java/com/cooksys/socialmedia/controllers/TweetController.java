package com.cooksys.socialmedia.controllers;

import java.util.List;

import com.cooksys.socialmedia.services.TweetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweet")
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public ResponseEntity<List<TweetResponseDto>> getAllTweets() {
        List<TweetResponseDto> tweets = tweetService.getAllTweets();
        return ResponseEntity.ok(tweets);
    }

}
