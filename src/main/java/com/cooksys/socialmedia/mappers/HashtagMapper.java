package com.cooksys.socialmedia.mappers;

import com.cooksys.socialmedia.dtos.HashtagDto;
import com.cooksys.socialmedia.entities.Hashtag;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between {@link Hashtag} entities and {@link HashtagDto} data transfer objects.
 * Utilizes MapStruct for implementation generation, providing an abstraction layer for entity-DTO conversions.
 */
@Mapper(componentModel = "spring")
public interface HashtagMapper {

    /**
     * Converts a {@link Hashtag} entity to a {@link HashtagDto}.
     *
     * @param hashtag the {@link Hashtag} entity to convert
     * @return the corresponding {@link HashtagDto}
     */
    HashtagDto toDto(Hashtag hashtag);

    /**
     * Converts a {@link HashtagDto} to a {@link Hashtag} entity.
     * This method can be useful for creating or updating entities based on DTO data.
     *
     * @param hashtagDto the {@link HashtagDto} to convert
     * @return the corresponding {@link Hashtag} entity
     */
    Hashtag toEntity(HashtagDto hashtagDto);

    HashtagDto entityToDto(Hashtag hashtag);
}
