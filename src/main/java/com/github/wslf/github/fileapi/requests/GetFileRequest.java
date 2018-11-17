package com.github.wslf.github.fileapi.requests;

import com.github.wslf.github.User;

import javax.annotation.Nullable;

public class GetFileRequest extends FileRequest {
  @Nullable
  private String ref;

  public GetFileRequest(User user, String repositoryName, String filePath, String ref) {
    super(user, repositoryName, filePath);
    this.ref = ref;
  }

  @Nullable
  public String getRef() {
    return ref;
  }
}
