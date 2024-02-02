package com.cooksys.socialmedia.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Embeddable class for user credentials, containing username and password. This class will be
 * embedded in the User entity and mapped to the same table. Note: Always handle passwords securely
 * in production.
 */
@Embeddable
@Data
@NoArgsConstructor
public class Credentials {

  /** Unique username for a user. */
  @Column(nullable = false, unique = true)
  private String username;

  /**
   * User's password. Used for login authentication. Note: Stored as plain text here for simplicity,
   * which is not secure for production.
   */
  @Column(nullable = false)
  private String password;
}
