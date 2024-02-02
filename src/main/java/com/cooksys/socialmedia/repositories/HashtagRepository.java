package com.cooksys.socialmedia.repositories;

import com.cooksys.socialmedia.entities.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for {@link Hashtag} instances.
 * Provides an abstraction layer for database operations on hashtags.
 */
@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    /**
     * Checks if a hashtag with the specified label exists in the database.
     *
     * @param label the label of the hashtag
     * @return true if an entity with the given label exists, false otherwise
     */
    boolean existsByLabel(String label);

    /**
     * Finds a hashtag by its label.
     *
     * @param label the label of the hashtag to find
     * @return an {@link Optional} containing the {@link Hashtag} if found, or empty if not
     */
    Optional<Hashtag> findByLabel(String label);

}