package com.cooksys.socialmedia.controllers;

import java.util.List;

import com.cooksys.socialmedia.exceptions.BadRequestException;
import com.cooksys.socialmedia.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.dtos.TweetResponseDto;
import com.cooksys.socialmedia.dtos.UserRequestDto;
import com.cooksys.socialmedia.dtos.UserResponseDto;
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
	
	@PostMapping
	public UserResponseDto createUser(@RequestBody UserRequestDto newUser) {
		return userService.createUser(newUser);
	}
	
	@GetMapping("/@{username}")
	public UserResponseDto getUserByUsername(@PathVariable("username") String username) {
		return userService.getUserByUsername(username);
	}
	
	@PatchMapping("/@{username}")
	public UserResponseDto updateUserProfile(@PathVariable("username") String username, @RequestBody UserRequestDto updatedUser) {
		return userService.updateUserProfile(username, updatedUser);
	}
	
	@DeleteMapping("/@{username}")
	public UserResponseDto deleteUser(@PathVariable("username") String username) {
		return userService.deleteUser(username);
	}

	@PostMapping("/@{username}/follow")
	public ResponseEntity<Void> followUser(@PathVariable("username") String username, @RequestBody CredentialsDto newFollower) {
		userService.followUser(username, newFollower);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/@{username}/unfollow")
	public void unfollowUser(@PathVariable("username") String username, @RequestBody CredentialsDto follower) {
		userService.unfollowUser(username, follower);
	}
	
	@GetMapping("/@{username}/feed")
	public List<TweetResponseDto> getFeed(@PathVariable("username") String username) {
		return userService.getFeed(username);
	}
	
	@GetMapping("/@{username}/tweets") 
	public List<TweetResponseDto> getTweets(@PathVariable("username") String username) {
		return userService.getTweets(username);
	}	
	
	@GetMapping("/@{username}/mentions") 
	public List<TweetResponseDto> getMentions(@PathVariable("username") String username) {
		return userService.getMentions(username);
	}

	@GetMapping("/@{username}/followers")
	public List<UserResponseDto> getUserFollowers(@PathVariable String username) {
		return userService.getFollowersByUsername(username);
	}
	
	@GetMapping("/@{username}/following") 
	public List<UserResponseDto> getFollowing(@PathVariable("username") String username) {
		return userService.getFollowing(username);
	}
}
