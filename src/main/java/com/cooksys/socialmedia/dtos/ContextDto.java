package com.cooksys.socialmedia.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for the reply context of a tweet.
 * Includes the target tweet, the chain of replies leading to the target tweet (before),
 * and the chain of replies that followed the target tweet (after).
 */
@NoArgsConstructor
@Data
public class ContextDto {
    //TODO: Uncomment and test when Tweets are implemented
//    /**
//     * The target tweet for which the context is provided.
//     */
//    private TweetDto target;
//
//    /**
//     * The chain of tweets (in chronological order) that led to the target tweet.
//     */
//    private List<TweetDto> before;
//
//    /**
//     * The chain of tweets (in chronological order) that followed the target tweet.
//     * This includes all replies of replies, meaning that all branches of replies
//     * are flattened into a single chronological list.
//     */
//    private List<TweetDto> after;
}
