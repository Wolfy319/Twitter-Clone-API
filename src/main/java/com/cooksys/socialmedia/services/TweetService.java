package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.embeddable.Credentials;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();

	List<TweetResponseDto> getReplies(Long id);

	List<UserResponseDto> getLikes(Long id);

	TweetResponseDto repostTweet(Long id, Credentials credentials);
}
