package com.github.wslf.githubfileapi;


/**
 * @author Wsl_F
 */
public class GithubUser {
  private final String name;
  private final String token;

  public GithubUser(String name, String token) {
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