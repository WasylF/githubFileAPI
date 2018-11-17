package com.github.wslf.github.fileapi.requests;

import com.github.wslf.github.User;
import com.github.wslf.github.fileapi.Constants;
import com.google.api.client.http.GenericUrl;

public class FileRequest {
  protected User user;
  protected String repositoryName;
  protected String filePath;

  public FileRequest(User user, String repositoryName, String filePath) {
    this.user = user;
    this.repositoryName = repositoryName;
    this.filePath = filePath;
  }

  public GenericUrl buildUrl() {
    String url = String.format("%s%s/%s/contents/%s", Constants.GITHUB_API_REPOS, user.getName(), repositoryName, filePath);
    return new GenericUrl(url);
  }

  public boolean hasAuth() {
    return getAuth() != null;
  }

  public String getAuth() {
    return user.getAuth();
  }
}
