package com.cooksys.socialmedia.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
//import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;
import com.cooksys.socialmedia.services.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final ValidateService validateService;

	@Override
	public List<UserResponseDto> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResponseDto createUser(@RequestBody UserRequestDto newUserRequest) {
		String username = newUserRequest.getCredentials().getUsername();
		User existingUser = userRepository.findByCredentialsUsername(username);
		boolean userExists = existingUser != null;
		// Throw an exception if any required field is missing
		if (username == null || newUserRequest.getCredentials().getPassword() == null
							 || newUserRequest.getProfile().getEmail() == null) {
			throw new BadRequestException("Request is missing one or more required fields");
		} 
		// Throw an exception if the user exists and isn't flagged as deleted in the DB
		else if (userExists && !existingUser.isDeleted()) {
			throw new BadRequestException("User already exists");
		} 
		// Reactivate user account if it exists in the DB but has been deleted
		else if (userExists) {
			existingUser.setDeleted(false);
			return userMapper.entityToDto(userRepository.saveAndFlush(existingUser));
		}
		
		// Create the new user
		User newUser = userMapper.dtoToEntity(newUserRequest);
		return userMapper.entityToDto(userRepository.saveAndFlush(newUser));
	}

	@Override
	public UserResponseDto getUserByUsername(String username) {
		User returnedUser = userRepository.findByCredentialsUsername(username);
		if (returnedUser == null || returnedUser.isDeleted()) {
			throw new NotFoundException("User does not exist");
		}
		return userMapper.entityToDto(returnedUser);
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
