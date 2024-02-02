package com.cooksys.socialmedia.mappers;

import org.mapstruct.Mapper;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.embeddable.Credentials;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

  CredentialsDto toDto(Credentials credentials);

  Credentials toEntity(CredentialsDto credentialsDto);
}
