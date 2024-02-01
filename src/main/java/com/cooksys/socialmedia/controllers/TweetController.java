package com.cooksys.socialmedia.controllers;

import java.util.List;

import com.cooksys.socialmedia.dtos.*;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.services.TweetService;
import org.springframework.web.bind.annotation.*;

import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponseDto> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTags(@PathVariable long id) {
        return tweetService.getTags(id);
    }

}
