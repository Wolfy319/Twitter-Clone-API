package com.cooksys.socialmedia.services;

import java.util.List;

import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;

public interface UserService {

	List<UserResponseDto> getUsers();

	UserResponseDto createUser(UserRequestDto newUser);

	UserResponseDto getUserByUsername(String username);

	UserResponseDto updateUserProfile(String username, UserRequestDto updatedUser);

	UserResponseDto deleteUser(String username);

	void followUser(String username, UserRequestDto newFollower);

	void unfollowUser(String username, UserRequestDto follower);
	
	List<TweetResponseDto> getFeed(String username);

	List<TweetResponseDto> getTweets(String username);

	List<TweetResponseDto> getMentions(String username);

	List<UserResponseDto> getFollowers(String username);

	List<UserResponseDto> getFollowing(String username);
}
