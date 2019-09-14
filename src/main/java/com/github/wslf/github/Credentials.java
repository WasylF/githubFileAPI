package com.github.wslf.github;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Wsl_F
 */
public class Credentials {
  private final String name;
  @Nullable
  private final String token;

  public Credentials(String name) {
    this.name = name;
    this.token = null;
  }

  public Credentials(String name, @Nonnull String token) {
    this.name = name;
    this.token = token;
  }

  public String getName() {
    return name;
  }

  public String getAuth() {
    return "token " + token;
  }
}