package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();

    List<HashtagDto> getTags(long id);

    List<TweetResponseDto> getReposts(long id);

    List<UserResponseDto> getMentions(long id);
}
