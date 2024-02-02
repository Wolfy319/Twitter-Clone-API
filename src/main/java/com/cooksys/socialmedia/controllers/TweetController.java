package com.cooksys.socialmedia.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.socialmedia.dtos.ContextDto;
import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.embeddable.Credentials;
import com.cooksys.socialmedia.services.TweetService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    @GetMapping
    public ResponseEntity<List<TweetResponseDto>> getAllTweets() {
        List<TweetResponseDto> tweets = tweetService.getAllTweets();
        return ResponseEntity.ok(tweets);
    }

    @PostMapping
    public ResponseEntity<TweetResponseDto> createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
        TweetResponseDto createdTweet = tweetService.createTweet(tweetRequestDto);
        return new ResponseEntity<>(createdTweet, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetResponseDto> getTweetById(@PathVariable Long id) {
        Optional<TweetResponseDto> tweetDto = tweetService.getTweetById(id);
        return tweetDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
    public List<HashtagDto> getTags(@PathVariable Long id) {
        return tweetService.getTags(id);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getReposts(@PathVariable Long id) {
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
    
    @PostMapping("/{id}/like")
    public void likeTweet(@PathVariable Long id, @RequestBody Credentials credentials) {
        tweetService.likeTweet(id, credentials);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweet(@PathVariable Long id, CredentialsDto credentials) {
        return tweetService.deleteTweet(id, credentials);
    }
}
