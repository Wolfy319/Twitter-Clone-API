package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.HashtagDto;

import java.util.List;
import java.util.Set;

/**
 * Service interface for managing hashtags.
 * Provides methods to perform operations related to hashtags within the application.
 */
public interface HashtagService {

    /**
     * Retrieves a list of all hashtags.
     *
     * @return a list of {@link HashtagDto} objects representing all hashtags tracked by the database
     */
    List<HashtagDto> getAllHashtags();

    //TODO: Uncomment and test when Tweets are implemented
//    /**
//     * Retrieves all tweets tagged with a specific hashtag.
//     *
//     * @param label the label of the hashtag
//     * @return a list of {@link TweetDto} objects representing all tweets associated with the hashtag
//     */
//    List<TweetDto> getTweetsByHashtag(String label);
}
