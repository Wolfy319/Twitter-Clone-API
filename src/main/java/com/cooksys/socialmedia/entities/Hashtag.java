package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a hashtag in the system.
 */
@Entity
@NoArgsConstructor
@Data
public class Hashtag {

    @Id
    @GeneratedValue
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
    @CreationTimestamp
    private Timestamp firstUsed;

    /**
     * The timestamp of when the hashtag was last used.
     * It is updated every time a new tweet is tagged with this hashtag.
     */
    @UpdateTimestamp
    private Timestamp lastUsed;

    @ManyToMany(mappedBy = "hashtags", cascade = CascadeType.ALL)
    private List<Tweet> tweets = new ArrayList<>();

}




