package com.cooksys.socialmedia.services.impl;

// TODO: Uncomment and test when Tweets are implemented
// import com.cooksys.socialmedia.dtos.TweetDto;
// import com.cooksys.socialmedia.repositories.TweetRepository;
// import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.entities.Hashtag;
import com.cooksys.socialmedia.mappers.HashtagMapper;
import com.cooksys.socialmedia.repositories.HashtagRepository;
import com.cooksys.socialmedia.services.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the HashtagService interface.
 * Handles business logic related to hashtags.
 */
@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;
    //TODO: Uncomment and test when Tweets are implemented
//    private final TweetRepository tweetRepository;
//    private final TweetMapper tweetMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HashtagDto> getAllHashtags() {
        List<Hashtag> hashtags = hashtagRepository.findAll();
        return hashtags.stream()
                .map(hashtagMapper::toDto)
                .collect(Collectors.toList());
    }

    //TODO: Implement findByHashtags_LabelAndDeletedFalseOrderByPostedDesc() when Tweets are implemented
    //TODO: Uncomment and test
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public List<TweetDto> getTweetsByHashtag(String label) {
//        // Check if the hashtag exists
//        if (!hashtagRepository.existsByLabel(label)) {
//            throw new HashtagNotFoundException("Hashtag not found with label: " + label);
//        }
//
//        // Fetch the tweets by the hashtag label, ensuring they are not deleted and ordered by posted date in descending order
//        List<Tweet> tweets = tweetRepository.findByHashtags_LabelAndDeletedFalseOrderByPostedDesc(label);
//        return tweets.stream()
//                .map(tweetMapper::toDto) // Convert each Tweet entity to TweetDto
//                .collect(Collectors.toList());
//    }
}
