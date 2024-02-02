package com.cooksys.socialmedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.socialmedia.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByCredentialsUsername(String username);

  List<User> findByDeletedFalse();

  User findByCredentialsUsername(String username);

  Optional<User> findByCredentialsUsernameAndDeletedFalse(String username);

  Optional<User> findByCredentials_Username(String username);
}
