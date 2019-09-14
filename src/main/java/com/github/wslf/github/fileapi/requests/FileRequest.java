package com.github.wslf.github.fileapi.requests;

import com.github.wslf.github.Credentials;
import com.github.wslf.github.fileapi.Constants;
import com.google.api.client.http.GenericUrl;

public class FileRequest extends GenericUrl {
  protected Credentials credentials;
  protected String repositoryName;
  protected String filePath;

  public FileRequest(Credentials credentials, String repositoryName, String filePath) {
    super(String.format("%s%s/%s/contents/%s", Constants.GITHUB_API_REPOS, credentials.getName(), repositoryName,
        filePath));
    this.credentials = credentials;
    this.repositoryName = repositoryName;
    this.filePath = filePath;
  }

  public boolean hasAuth() {
    return getAuth() != null;
  }

  public String getAuth() {
    return credentials.getAuth();
  }
}
