package com.cooksys.socialmedia.dtos;

import com.cooksys.socialmedia.entities.Profile;
//import com.cooksys.socialmedia.entities.Credentials;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {
//	private Credentials credentials;
	private Profile profile;

}