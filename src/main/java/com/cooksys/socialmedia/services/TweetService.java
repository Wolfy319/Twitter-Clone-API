package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.embeddable.Credentials;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();

	List<TweetResponseDto> getReplies(Long id);

	List<UserResponseDto> getLikes(Long id);

	TweetResponseDto repostTweet(Long id, Credentials credentials);

    List<HashtagDto> getTags(long id);

    List<TweetResponseDto> getReposts(long id);

    List<UserResponseDto> getMentions(long id);

    void likeTweet(long id, Credentials credentials);

    TweetResponseDto deleteTweet(long id, CredentialsDto credentials);
}
