package com.github.wslf.github.fileapi.requests;

import com.github.wslf.github.User;
import com.github.wslf.github.fileapi.Constants;
import com.google.api.client.http.GenericUrl;

public class FileRequest extends GenericUrl {
  protected User user;
  protected String repositoryName;
  protected String filePath;

  public FileRequest(User user, String repositoryName, String filePath) {
    super(String.format("%s%s/%s/contents/%s", Constants.GITHUB_API_REPOS, user.getName(), repositoryName, filePath));
    this.user = user;
    this.repositoryName = repositoryName;
    this.filePath = filePath;
  }

  public boolean hasAuth() {
    return getAuth() != null;
  }

  public String getAuth() {
    return user.getAuth();
  }
}
