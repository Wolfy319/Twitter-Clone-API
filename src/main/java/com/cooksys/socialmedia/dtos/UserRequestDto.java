package com.cooksys.socialmedia.dtos;

import com.cooksys.socialmedia.entities.Profile;
//import com.cooksys.socialmedia.entities.Credentials;

import jakarta.persistence.Embedded;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {
	@Embedded
	private CredentialsDto credentials;
	private Profile profile;

}
