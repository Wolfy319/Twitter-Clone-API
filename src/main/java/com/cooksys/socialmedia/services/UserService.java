package com.cooksys.socialmedia.services;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import java.util.List;

public interface UserService {

	List<UserResponseDto> getUsers();

	UserResponseDto createUser(UserRequestDto newUser);

	UserResponseDto getUserByUsername(String username);

	UserResponseDto updateUserProfile(String username, UserRequestDto updatedUser);

	UserResponseDto deleteUser(String username);

	void followUser(String username, CredentialsDto newFollower);

	void unfollowUser(String username, CredentialsDto follower);
	
	List<TweetResponseDto> getFeed(String username);

	List<TweetResponseDto> getTweets(String username);

	List<TweetResponseDto> getMentions(String username);

	List<UserResponseDto> getFollowers(String username);

	List<UserResponseDto> getFollowing(String username);

	List<UserResponseDto> getFollowersByUsername(String username);
}
