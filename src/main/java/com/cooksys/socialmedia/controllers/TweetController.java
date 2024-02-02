package com.cooksys.socialmedia.controllers;

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

}
