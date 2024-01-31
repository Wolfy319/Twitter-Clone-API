package com.cooksys.socialmedia.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cooksys.socialmedia.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByCredentialsUsername(String username);
    User findByCredentialsUsername(String username);
}
