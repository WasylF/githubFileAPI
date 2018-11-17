package com.github.wslf.github.fileapi;

import com.github.wslf.github.User;
import com.github.wslf.github.fileapi.requests.CreateFileRequest;
import com.github.wslf.github.fileapi.requests.FileRequest;
import com.github.wslf.github.fileapi.requests.GetFileRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.gson.GsonFactory;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class FileAPI {
  private final RequestProcessor requestProcessor = new RequestProcessor();
  private static final GsonFactory GSON_FACTORY = new GsonFactory();

  public static void main(String[] args) throws IOException {
    FileAPI api = new FileAPI();
    User user = new User("WslF", "...");
    String repo = "Test";
    String filePath = "folder1/f2/f3/f4/test6.txt";
    String content = "this is content of file test 6!";
    api.createFile(user, repo, filePath, content, "commit test 5");
    String contentResponse = api.getFile(user, repo, filePath, null);
    if (content.equals(contentResponse)) {
      System.out.println("It works");
    }
  }

  public boolean createFile(User user, String repositoryName, String filePath, String content, String commitMessage) throws IOException {
    FileRequest createFileRequest = new CreateFileRequest(user, repositoryName, filePath, commitMessage, content);

    HttpResponse response = requestProcessor.sendPutRequest(createFileRequest);

    return response.isSuccessStatusCode();
  }

  public String getFile(User user, String repositoryName, String filePath, @Nullable String ref) throws IOException {
    GetFileRequest createFileRequest = new GetFileRequest(user, repositoryName, filePath, ref);

    HttpResponse response = requestProcessor.sendGetRequest(createFileRequest);

    JsonParser parser = GSON_FACTORY.createJsonParser(response.getContent(), StandardCharsets.UTF_8);
    parser.skipToKey("content");
    String content = parser.getText().stripTrailing();

    return new String(Base64.getDecoder().decode(content), StandardCharsets.UTF_8);
  }

}
