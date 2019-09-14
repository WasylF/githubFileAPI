package com.github.wslf.github.fileapi.requests;

import com.github.wslf.github.Credentials;
import com.google.api.client.util.Key;

import javax.annotation.Nullable;

public class DeleteFileRequest extends FileRequest {

  @Key
  final String message;
  @Key
  final String sha;
  @Key
  @Nullable
  final String branch;

  public DeleteFileRequest(Credentials credentials, String repositoryName, String filePath, String message, String sha,
                           String branch) {
    super(credentials, repositoryName, filePath);
    this.message = message;
    this.sha = sha;
    this.branch = branch;
  }
}
