package com.cooksys.socialmedia.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TweetRequestDto {
    // TODO: Waiting to implement depending on controller
	private String content;
    private CredentialsDto credentials;
}
