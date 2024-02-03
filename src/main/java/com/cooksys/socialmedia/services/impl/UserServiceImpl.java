package com.cooksys.socialmedia.services.impl;

import static java.util.stream.Collectors.toList;

import com.cooksys.socialmedia.dtos.*;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import com.cooksys.socialmedia.mappers.ProfileMapper;
import com.cooksys.socialmedia.mappers.TweetMapper;
import com.cooksys.socialmedia.mappers.UserMapper;
import com.cooksys.socialmedia.repositories.TweetRepository;
import com.cooksys.socialmedia.repositories.UserRepository;
import com.cooksys.socialmedia.services.UserService;
import com.cooksys.socialmedia.services.ValidateService;
import com.cooksys.socialmedia.utils.TweetTimestampComparator;

import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final ValidateService validateService;
    private final ProfileMapper profileMapper;

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
    public UserResponseDto deleteUser(String username) {
        User userToDelete = userRepository.findByCredentialsUsername(username);
        if (userToDelete == null || userToDelete.isDeleted()) {
            throw new BadRequestException("User doesn't exist!");
        }
        userToDelete.setDeleted(true);
        return userMapper.entityToDto(userRepository.saveAndFlush(userToDelete));
    }

    @Override
    public void followUser(String username, CredentialsDto followerDto) {
    	if(followerDto == null || followerDto.getUsername() == null || followerDto.getPassword() == null) {
    		throw new BadRequestException("Credentials include errors or were not sent");
    	}

        User follower = userRepository.findByCredentials_Username(followerDto.getUsername())
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new NotFoundException("Follower user not found or not active"));

        User followable = userRepository.findByCredentials_Username(username)
                .filter(user -> !user.isDeleted())
                .orElseThrow(() -> new NotFoundException("User to follow not found or not active"));

        boolean alreadyFollowing = followable.getFollowers().stream()
                .anyMatch(existingFollower -> existingFollower.getId().equals(follower.getId()));

        if (alreadyFollowing) {
            throw new BadRequestException("The user is already following the target user");
        }

        followable.getFollowers().add(follower);
        userRepository.save(followable);
    }

	@Override
	public void unfollowUser(String username, CredentialsDto followerRequest) {
		User userToUnfollow = userRepository.findByCredentialsUsername(username);
    	if(followerRequest == null || followerRequest.getUsername() == null || followerRequest.getPassword() == null) {
    		throw new BadRequestException("Credentials include errors or were not sent");
    	}

		User user = userRepository.findByCredentialsUsername(followerRequest.getUsername());
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
		user.setFollowing(following);
		userToUnfollow.setFollowers(followers);
		userRepository.saveAndFlush(user);
		userRepository.saveAndFlush(userToUnfollow);

    }

    @Override
    public List<TweetResponseDto> getFeed(String username) {

        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found or deleted");
        }

        User user = optionalUser.get();

        List<Tweet> userTweets = user.getTweets().stream()
                .filter(tweet -> !tweet.isDeleted())
                .collect(Collectors.toList());

        List<User> followedUsers = user.getFollowing();

        for (User followedUser : followedUsers) {
            userTweets.addAll(followedUser.getTweets().stream()
                    .filter(tweet -> !tweet.isDeleted())
                    .toList());
        }

        return userTweets.stream()
                .map(tweetMapper::entityToDto)
                .sorted(Comparator.comparing(TweetResponseDto::getPosted).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<TweetResponseDto> getTweets(String username) {

        User user = userRepository.findByCredentialsUsername(username);

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
                .collect(toList());
    }

    public List<UserResponseDto> getFollowersByUsername(String username) {

        User user = userRepository.findByCredentialsUsernameAndDeletedFalse(username)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));


        return user.getFollowers().stream()
                .map(userMapper::entityToDto)
                .collect(toList());
    }

  public UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto) {
  	if(userRequestDto.getCredentials() == null || userRequestDto.getCredentials().getUsername() == null 
  			|| userRequestDto.getCredentials().getPassword() == null || userRequestDto.getProfile() == null) {
		throw new BadRequestException("Request missing required fields");
	}
    // Validate the user credentials
    User user = validateService.validateUser(userRequestDto.getCredentials());

    // Check if the username in path matches the username in credentials and if the user is not
    // deleted
    if (!user.getCredentials().getUsername().equals(username) || user.isDeleted()) {
      throw new BadRequestException("Username mismatch or user is deleted.");
    }

    // Map and set the new profile data
	User updatedUser = userMapper.dtoToEntity(userRequestDto);

    if(updatedUser.getProfile().getEmail() == null && updatedUser.getProfile().getEmail() == null
    		&& updatedUser.getProfile().getEmail() == null && updatedUser.getProfile().getEmail() == null) {
    	updatedUser.setProfile(user.getProfile());
    }

    // Convert the updated user entity to DTO
    return userMapper.entityToDto(userRepository.save(user));
  }
}
