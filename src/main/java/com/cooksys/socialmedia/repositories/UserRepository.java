package com.cooksys.socialmedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.socialmedia.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByCredentialsUsername(String username);

    List<User> findByDeletedFalse();

    User findByCredentials_Username(String username);
    User findByCredentialsUsername(String username);

    User findByCredentialsUsernameAndDeletedFalse(String username);

}
