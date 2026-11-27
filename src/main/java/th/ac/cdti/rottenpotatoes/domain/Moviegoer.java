package th.ac.cdti.rottenpotatoes.domain;

import java.util.Objects;

/** A person who uses Rotten Potatoes. */
public record Moviegoer(String name, String email) {

  /** Checks that the name is not blank and the email contains @. */
  public Moviegoer {
    Objects.requireNonNull(name, "name");
    Objects.requireNonNull(email, "email");
    if (name.isBlank()) {
      throw new IllegalArgumentException("name must not be blank");
    }
    if (!email.contains("@")) {
      throw new IllegalArgumentException("email must contain @");
    }
  }
}
