package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.CredentialsDto;
import com.cooksys.socialmedia.embeddable.Credentials;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CredentialsMapper {

    CredentialsMapper INSTANCE = Mappers.getMapper(CredentialsMapper.class);

    CredentialsDto toDto(Credentials credentials);

    Credentials toEntity(CredentialsDto credentialsDto);
}
