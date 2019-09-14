package com.github.wslf.github.fileapi.requests;

import com.github.wslf.github.Credentials;
import com.google.api.client.util.Key;

import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CreateFileRequest extends FileRequest {
  @Key
  private final String message;
  @Key
  private final String content;
  @Key
  @Nullable
  private final String branch;

  public CreateFileRequest(Credentials credentials, String repositoryName, String filePath, String commitMessage,
                           String originalContent) {
    this(credentials, repositoryName, filePath, commitMessage, originalContent, null);
  }

  public CreateFileRequest(Credentials credentials, String repositoryName, String filePath, String commitMessage,
                           String originalContent, @Nullable String branch) {
    super(credentials, repositoryName, filePath);
    this.message = commitMessage;
    this.content = Base64.getEncoder().encodeToString(originalContent.getBytes(StandardCharsets.UTF_8));
    this.branch = branch;
  }
}
