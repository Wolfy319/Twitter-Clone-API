package com.cooksys.socialmedia.entities;

import java.sql.Timestamp;
import java.util.Set;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name = "user_table")
public class User {
	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	@Column(nullable = false)
	private CredentialsDto credentials;

	@Embedded
	@Column(nullable = false)
	private Profile profile;

	@Column(nullable = false)
	private Timestamp joined;

	private boolean deleted;
	
	@ManyToMany
	@JoinTable(
			name = "followers_following",
			joinColumns = @JoinColumn(name = "follower_id"),
			inverseJoinColumns = @JoinColumn(name = "following_id"))
	private Set<User> following;
	
	@ManyToMany
	@JoinTable(
			name = "followers_following",
			joinColumns = @JoinColumn(name = "following_id"),
			inverseJoinColumns = @JoinColumn(name = "follower_id"))
	private Set<User> followers;
	
//	@ManyToMany 
//	@JoinTable(
//			name = "user_likes",
//			joinColumns = @JoinColumn(name = "user_id"),
//			inverseJoinColumns = @JoinColumn(name = "tweet_id"))
//	private Set<Tweet> likedTweets;
	
//	@ManyToMany
//	@JoinTable(
//			name = "user_mentions",
//			joinColumns = @JoinColumn(name = "user_id"),
//			inverseJoinColumns = @JoinColumn(name = "tweet_id"))
//	private Set<Tweet> mentionedTweets;

}
