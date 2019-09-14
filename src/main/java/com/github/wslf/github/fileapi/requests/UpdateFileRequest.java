package com.github.wslf.github.fileapi.requests;

import com.github.wslf.github.Credentials;
import com.google.api.client.util.Key;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UpdateFileRequest extends FileRequest {
  @Key
  final String message;
  @Key
  final String sha;
  @Key
  @Nullable
  final String branch;
  @Key
  final String content;

  public UpdateFileRequest(Credentials credentials, String repositoryName, String filePath, String message, String sha,
                           String originalContent, @Nullable String branch) {
    super(credentials, repositoryName, filePath);
    this.message = message;
    this.sha = sha;
    this.content = Base64.getEncoder().encodeToString(originalContent.getBytes(StandardCharsets.UTF_8));
    this.branch = branch;
  }
}
