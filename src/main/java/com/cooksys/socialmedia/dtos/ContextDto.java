package com.cooksys.socialmedia.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for the reply context of a tweet.
 * Includes the target tweet, the chain of replies leading to the target tweet (before),
 * and the chain of replies that followed the target tweet (after).
 */
@NoArgsConstructor
@Data
public class ContextDto {
	private TweetResponseDto target;
    private List<TweetResponseDto> before;
    private List<TweetResponseDto> after;
}
