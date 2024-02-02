package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing hashtags. Provides methods to perform operations related to
 * hashtags within the application.
 */
public interface HashtagService {

  /**
   * Retrieves a list of all hashtags.
   *
   * @return a list of {@link HashtagDto} objects representing all hashtags tracked by the database
   */
  List<HashtagDto> getAllHashtags();

  /**
   * Retrieves all tweets tagged with a specific hashtag.
   *
   * @param label the label of the hashtag
   * @return a list of {@link TweetResponseDto} objects representing all tweets associated with the
   *     hashtag
   */
  Optional<List<TweetResponseDto>> getTweetsByHashtag(String label);
}
