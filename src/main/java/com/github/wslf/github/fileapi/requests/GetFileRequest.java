package com.github.wslf.github.fileapi.requests;

import com.github.wslf.github.Credentials;
import com.google.api.client.util.Key;

import javax.annotation.Nullable;

public class GetFileRequest extends FileRequest {
  @Nullable
  @Key
  private String ref;

  public GetFileRequest(Credentials credentials, String repositoryName, String filePath, String ref) {
    super(credentials, repositoryName, filePath);
    this.ref = ref;
  }

  @Nullable
  public String getRef() {
    return ref;
  }
}
