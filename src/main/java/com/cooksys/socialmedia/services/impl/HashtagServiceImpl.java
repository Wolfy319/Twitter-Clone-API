package com.cooksys.socialmedia.services.impl;


import static java.util.stream.Collectors.toList;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.services.HashtagService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of the HashtagService interface.
 * Handles business logic related to hashtags.
 */
@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HashtagDto> getAllHashtags() {
        List<Hashtag> hashtags = hashtagRepository.findAll();

        return hashtags.stream()
                .map(hashtagMapper::toDto)
                .collect(toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<List<TweetResponseDto>> getTweetsByHashtag(String label) {
        return hashtagRepository.findByLabel(label)
                .map(tweetRepository::findByHashtagsAndDeletedFalseOrderByPostedDesc)
                .map(tweets -> tweets.stream().map(tweetMapper::entityToDto)
                        .collect(toList()));
    }
}
