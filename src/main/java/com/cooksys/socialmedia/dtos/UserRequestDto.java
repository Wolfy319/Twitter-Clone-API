package com.cooksys.socialmedia.dtos;

import com.cooksys.socialmedia.embeddable.Credentials;
import com.cooksys.socialmedia.embeddable.Profile;

import jakarta.persistence.Embedded;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {
	@Embedded
	private Credentials credentials;
	private Profile profile;
}
