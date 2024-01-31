package com.cooksys.socialmedia.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Data Transfer Object for sending hashtag data back to the client.
 * This class is used for transferring data related to hashtags between different layers of the application,
 * mainly between the service layer and the clients of the API.
 */
@Data
@NoArgsConstructor
public class HashtagDto {

    /**
     * The unique identifier of the hashtag.
     */
    private Long id;

    /**
     * The unique, case-sensitive label of the hashtag.
     * Represents the text of the hashtag itself.
     */
    private String label;

    /**
     * The timestamp of when the hashtag was first used in a tweet.
     * It is set at the time the first tweet with this hashtag is created and should not be updated thereafter.
     */
    private Timestamp firstUsed;

    /**
     * The timestamp of when the hashtag was last used in a tweet.
     * Should be updated every time a new tweet with this hashtag is created.
     */
    private Timestamp lastUsed;
}
