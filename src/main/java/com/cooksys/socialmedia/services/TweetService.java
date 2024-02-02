package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetRequestDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.embeddable.Credentials;

import java.util.List;
import java.util.Optional;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();

	List<TweetResponseDto> getReplies(Long id);

	List<UserResponseDto> getLikes(Long id);

	TweetResponseDto repostTweet(Long id, Credentials credentials);

    List<HashtagDto> getTags(long id);

    List<TweetResponseDto> getReposts(long id);

    List<UserResponseDto> getMentions(long id);

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    Optional<TweetResponseDto> getTweetById(Long id);
}
