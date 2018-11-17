package com.github.wslf.github.fileapi.responses;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GetFileResponse extends FileResponse {
  private String content;
  private String sha;

  private boolean isDecoded = false;

  public GetFileResponse() {
  }

  public GetFileResponse(String content, String sha) {
    this.content = content;
    this.sha = sha;
  }

  public String getContent() {
    decodeContent();
    return content;
  }

  public String getSha() {
    return sha;
  }

  private void decodeContent() {
    if (!isDecoded) {
      this.content = new String(Base64.getDecoder().decode(content.strip()), StandardCharsets.UTF_8);
      isDecoded = true;
    }
  }
}
