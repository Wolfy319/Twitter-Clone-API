package com.cooksys.socialmedia.controllers;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller responsible for handling requests related to hashtags.
 * All endpoints in this controller are prefixed with "/tags".
 */
@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class HashtagController {

    private final HashtagService hashtagService;

    /**
     * Retrieves all hashtags tracked by the database.
     *
     * @return a ResponseEntity containing a list of all hashtags (HashtagDto)
     */
    @GetMapping
    public ResponseEntity<List<HashtagDto>> getAllHashtags() {
        List<HashtagDto> hashtags = hashtagService.getAllHashtags();
        return ResponseEntity.ok(hashtags);
    }

    //TODO: Uncomment and test when Tweets are implemented
//    /**
//     * Retrieves all (non-deleted) tweets tagged with the given hashtag label.
//     * The tweets should appear in reverse-chronological order.
//     * If no hashtag with the given label exists, an error should be sent in lieu of a response.
//     *
//     * @param label the label of the hashtag for which to retrieve tweets
//     * @return a ResponseEntity containing a list of tweets (TweetResponseDto) tagged with the hashtag
//     */
//    @GetMapping("/{label}")
//    public ResponseEntity<List<TweetResponseDto>> getTweetsByHashtag(@PathVariable String label) {
//        List<TweetResponseDto> tweets = hashtagService.getTweetsByHashtag(label);
//        return tweets.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(tweets);
//    }
}
