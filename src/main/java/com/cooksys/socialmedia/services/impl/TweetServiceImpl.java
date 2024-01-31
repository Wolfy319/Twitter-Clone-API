package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.services.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TweetServiceImpl implements TweetService {
    @Override
    public List<TweetResponseDto> getAllTweets() {
        return null;
    }
}
