package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.TweetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TweetServiceImpl implements TweetService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TweetMapper tweetMapper;
    @Autowired
    private TweetRepository tweetRepository;
    @Autowired
    private HashtagMapper hashtagMapper;

    @Override
    public List<TweetResponseDto> getAllTweets() {
        return null;
    }

    @Override
    public List<HashtagDto> getTags(long id) {

        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new NotFoundException("Tweet not found"));

        return tweet.getHashtags().stream()
                .map(hashtagMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
