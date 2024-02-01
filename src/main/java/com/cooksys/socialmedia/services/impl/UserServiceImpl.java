package com.cooksys.socialmedia.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
//import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.services.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TweetMapper tweetMapper;

	@Override
	public List<UserResponseDto> getUsers() {

		List<User> users = userRepository.findByDeletedFalse();

		List<UserResponseDto> userResponseDtos = userMapper.entitiesToDtos(users);

		return userResponseDtos;
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

	@Override
	public List<TweetResponseDto> getFeed(String username) {

		User user = userRepository.findByCredentials_Username(username);

		if (user == null) {
			throw new NotFoundException("User not found");
		}

		List<User> following = getFollowing(username);

		List<Tweet> tweetsFromFollowing = new ArrayList<>();

		for (User followingUser : following) {
			tweetsFromFollowing.addAll(followingUser.getTweets());
		}

		List<TweetResponseDto> tweetResponseDtos = new ArrayList<>();

		for (Tweet tweet : tweetsFromFollowing) {
			tweetResponseDtos.add(tweetMapper.entityToDto(tweet));
		}

		return tweetResponseDtos;
	}

	@Override
	public List<TweetResponseDto> getTweets(String username) {

		User user = userRepository.findByCredentials_Username(username);

		if (user == null) {
			throw new NotFoundException("User not found");
		}

		List<Tweet> userTweets = new ArrayList<>();

		userTweets.addAll(user.getTweets());

		List<TweetResponseDto> tweetResponseDtos = new ArrayList<>();

		for (Tweet tweet : userTweets) {
			tweetResponseDtos.add(tweetMapper.entityToDto(tweet));
		}
		return tweetResponseDtos;
	}
//
//	@Override
//	public List<TweetResponseDto> getMentions(String usernamea) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<User> getFollowers(String username) {
		return List.of();
	}

	@Override
	public List<User> getFollowing(String username) {
		return List.of();
	}

}
