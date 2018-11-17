package com.github.wslf.githubfileapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.github.wslf.githubfileapi.Constants.GITHUB_API_REPOS;

class CreateFileRequest {
  private static final Gson GSON = new GsonBuilder().create();

  private final String message;
  private final String content;

  CreateFileRequest(String commitMessage, String originalContent) {
    this.message = commitMessage;
    this.content = Base64.getEncoder().encodeToString(originalContent.getBytes(StandardCharsets.UTF_8));
  }

  String buildBody() {
    return GSON.toJson(this);
  }

}
