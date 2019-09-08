package com.github.wslf.github.fileapi.requests;

import com.github.wslf.github.User;
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

  public CreateFileRequest(User user, String repositoryName, String filePath, String commitMessage,
                           String originalContent) {
    this(user, repositoryName, filePath, commitMessage, originalContent, null);
  }

  public CreateFileRequest(User user, String repositoryName, String filePath, String commitMessage,
                           String originalContent, @Nullable String branch) {
    super(user, repositoryName, filePath);
    this.message = commitMessage;
    this.content = Base64.getEncoder().encodeToString(originalContent.getBytes(StandardCharsets.UTF_8));
    this.branch = branch;
  }
}
