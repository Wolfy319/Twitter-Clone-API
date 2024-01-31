package com.cooksys.socialmedia.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    // @JoinColumn(table = "author", referencedColumnName = "id")
    // Commented out pending more info
    private User author;

    private Timestamp posted;

    private boolean deleted;

    private String content;

    @ManyToOne
    private Tweet repostOf;

    @OneToMany
    private List<Tweet> reposted;

    @ManyToOne
    private Tweet inReplyTo;

    @OneToMany
    private List<Tweet> replies;

    @ManyToMany(mappedBy = "likedTweets")
    private List<User> likedBy;

    @ManyToMany(mappedBy = "mentionedTweets")
    private List<User> mentionedBy;

 	@ManyToMany
	@JoinTable(
			name = "tweet_hashtags",
			joinColumns = @JoinColumn(name = "tweet_id"),
			inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
	private List<Hashtag> tweetHashtags;

}
