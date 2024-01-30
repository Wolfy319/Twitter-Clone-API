package com.cooksys.socialmedia.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
//import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.services.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {@Override
	public List<UserResponseDto> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResponseDto createUser(UserRequestDto newUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResponseDto getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResponseDto updateUserProfile(String username, UserRequestDto updatedUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResponseDto deleteUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void followUser(String username, UserRequestDto newFollower) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unfollowUser(String username, UserRequestDto follower) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public List<TweetResponseDto> getFeed(String username) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<TweetResponseDto> getTweets(String username) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<TweetResponseDto> getMentions(String usernamea) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<User> getFollowers(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getFollowing(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
