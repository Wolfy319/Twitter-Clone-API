package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.TweetResponseDto;

import java.util.List;

public interface TweetService {
    List<TweetResponseDto> getAllTweets();
}
