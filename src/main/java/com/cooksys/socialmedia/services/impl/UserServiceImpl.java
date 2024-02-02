package com.cooksys.socialmedia.services.impl;

import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;
import com.cooksys.socialmedia.utils.TweetTimestampComparator;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private final UserMapper userMapper;
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;

    @Override
    public List<UserResponseDto> getUsers() {

        List<User> users = userRepository.findByDeletedFalse();

        return userMapper.entitiesToDtos(users);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto newUserRequest) {
        User newUser = userMapper.dtoToEntity(newUserRequest);
        if (newUser.getCredentials() == null || newUser.getCredentials().getUsername() == null 
        		|| newUser.getCredentials().getPassword() == null || newUser.getProfile() == null
        		|| newUser.getProfile().getEmail() == null) {
            throw new BadRequestException("Request is missing one or more required fields");
        }
        String username = newUser.getCredentials().getUsername();
        User existingUser = userRepository.findByCredentialsUsername(username);
        boolean userExists = existingUser != null;
        // Throw an exception if any required field is missing

        // Throw an exception if the user exists and isn't flagged as deleted in the DB
        if (userExists && !existingUser.isDeleted()) {
            throw new BadRequestException("User already exists");
        }
        // Reactivate user account if it exists in the DB but has been deleted
        else if (userExists
                && newUser.getCredentials().equals(existingUser.getCredentials())) {
            existingUser.setDeleted(false);
            return userMapper.entityToDto(userRepository.saveAndFlush(existingUser));
        }

        // Create the new user
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
        User userToDelete = userRepository.findByCredentialsUsername(username);
        if (userToDelete == null || userToDelete.isDeleted()) {
            throw new BadRequestException("User doesn't exist!");
        }
        userToDelete.setDeleted(true);
        return userMapper.entityToDto(userRepository.saveAndFlush(userToDelete));
    }

    @Override
    public void followUser(String username, UserRequestDto newFollower) {
        // TODO Auto-generated method stub

    }

	@Override
	public void unfollowUser(String username, UserRequestDto followerRequest) {
		User user = userRepository.findByCredentialsUsername(username);
		User userToUnfollow = userRepository.findByCredentialsUsername(followerRequest.getCredentials().getUsername());
		if(user == null) {
			throw new NotFoundException("Credentials provided do not match any active user");
		} else if(userToUnfollow == null || userToUnfollow.isDeleted()) {
			throw new NotFoundException("No such followable user exists");
		} else if(!user.getFollowing().contains(userToUnfollow)) {
			throw new NotFoundException("Users do not share any relationship");
		}

		List<User> following = user.getFollowing();
		List<User> followers = userToUnfollow.getFollowers();
		following.remove(userToUnfollow);
		followers.remove(user);
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(userToUnfollow);

    }

    @Override
    public List<TweetResponseDto> getFeed(String username) {
        return List.of();
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
    public List<TweetResponseDto> getMentions(String username) {
        if (!userRepository.existsByCredentialsUsername(username)) {
            throw new NotFoundException("User doesn't exist!");
        }

        List<Tweet> mentions = userRepository.findByCredentialsUsername(username).getMentionedTweets();
        List<Tweet> nonDeletedMentions = new ArrayList<>();
        for (Tweet mentionedTweet : mentions) {
            if (!mentionedTweet.isDeleted()) {
                nonDeletedMentions.add(mentionedTweet);
            }
        }
        Collections.sort(nonDeletedMentions, new TweetTimestampComparator());
        return tweetMapper.entitiesToDtos(nonDeletedMentions);
    }

    @Override
    public List<UserResponseDto> getFollowers(String username) {

        User user = userRepository.findByCredentialsUsername(username);

        if (user == null || user.isDeleted()) {
            throw new NotFoundException("User not found");
        }

        List<User> followers = user.getFollowers();

        return followers.stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public List<UserResponseDto> getFollowing(String username) {

        User user = userRepository.findByCredentialsUsername(username);

        if (user == null || user.isDeleted()) {
            throw new NotFoundException("User not found");
        }

        List<User> followingUsers = user.getFollowing();

        return followingUsers.stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList());
    }

}
