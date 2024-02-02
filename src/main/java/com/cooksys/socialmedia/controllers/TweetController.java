package com.cooksys.socialmedia.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.socialmedia.dtos.ContextDto;
import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.embeddable.Credentials;
import com.cooksys.socialmedia.services.TweetService;

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

    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getLikes(@PathVariable Long id) {
    	return tweetService.getLikes(id);
    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getReplies(@PathVariable Long id) {
    	return tweetService.getReplies(id);
    }

    @PostMapping("/{id}/repost")
    public TweetResponseDto repostTweet(@PathVariable Long id, @RequestBody Credentials credentials) {
    	return tweetService.repostTweet(id, credentials);
    }

    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTags(@PathVariable long id) {
        return tweetService.getTags(id);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getReposts(@PathVariable long id) {
        return tweetService.getReposts(id);
    }

    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getMentions(@PathVariable Long id) {
        return tweetService.getMentions(id);
    }

    @GetMapping("/{id}/context")
    public ContextDto getContext(@PathVariable Long id) {
    	return tweetService.getContext(id);
    }
}
