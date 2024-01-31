package com.cooksys.socialmedia.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

/**
 * Entity representing a hashtag in the system.
 */
@Entity
@Table(name = "hashtag", uniqueConstraints = {@UniqueConstraint(columnNames = {"label"})})
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique label of the hashtag. It is case-sensitive and must be unique.
     */
    @Column(nullable = false, unique = true)
    private String label;

    /**
     * The timestamp of when the hashtag was first used.
     * It is set at the time of creation and never updated.
     */
    @Column(nullable = false, updatable = false)
    private Timestamp firstUsed;

    /**
     * The timestamp of when the hashtag was last used.
     * It is updated every time a new tweet is tagged with this hashtag.
     */
    @Column(nullable = false)
    private Timestamp lastUsed;

//    @ManyToMany(mappedBy = "tweetHashtags")
//    private List<Tweet> tweets;

}




