package com.cooksys.socialmedia.controllers;

import java.util.List;

import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.Tweet;
import com.cooksys.socialmedia.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.entities.User;
import com.cooksys.socialmedia.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	
	@GetMapping
	public List<UserResponseDto> getUsers() {
		return userService.getUsers();
	}

	@GetMapping("/@{username}/feed")
	public List<TweetResponseDto> getFeed(@PathVariable String username) {
		return userService.getFeed(username);
	}

	@GetMapping("/@{username}/tweets")
	public List<TweetResponseDto> getTweets(@PathVariable String username) {
		return userService.getTweets(username);
	}

	@PostMapping
	public UserResponseDto createUser(UserRequestDto newUser) {
		return userService.createUser(newUser);
	}
	
	@GetMapping("/@{username}")
	public UserResponseDto getUserByUsername(@PathVariable String username) {
		return userService.getUserByUsername(username);
	}
	
	@PatchMapping("/@{username}")
	public UserResponseDto updateUserProfile(@PathVariable String username, UserRequestDto updatedUser) {
		return userService.updateUserProfile(username, updatedUser);
	}
	
	@DeleteMapping("/@{username}")
	public UserResponseDto deleteUser(@PathVariable String username) {
		return userService.deleteUser(username);
	}
	
	@PostMapping("/@{username}/follow") 
	public void followUser(@PathVariable String username, UserRequestDto newFollower) {
		userService.followUser(username, newFollower);
	}
	
	@PostMapping("/@{username}/unfollow")
	public void unfollowUser(@PathVariable String username, UserRequestDto follower) {
		userService.unfollowUser(username, follower);
	}
	
//	@GetMapping("/@{username}/feed")
//	public List<TweetResponseDto> getFeed(@PathVariable String username) {
//		return userService.getFeed(username);
//	}
//	
//	@GetMapping("/@{username}/tweets") 
//	public List<TweetResponseDto> getTweets(@PathVariable String username) {
//		return userService.getTweets(username);
//	}	
//	
//	@GetMapping("/@username/mentions") 
//	public List<TweetResponseDto> getMentions(@PathVariable String username) {
//		return userService.getMentions(username);
//	}
	
	@GetMapping("/@{username}/followers") 
	public List<User> getFollowers(@PathVariable String username) {
		return userService.getFollowers(username);
	}
	
	@GetMapping("/@{username}/following") 
	public List<User> getFollowing(@PathVariable String username) {
		return userService.getFollowing(username);
	}
}
