package com.github.wslf.github.fileapi;

import com.github.wslf.github.User;
import com.github.wslf.github.fileapi.requests.CreateFileRequest;
import com.github.wslf.github.fileapi.requests.FileRequest;
import com.github.wslf.github.fileapi.requests.GetFileRequest;
import com.github.wslf.github.fileapi.responses.GetFileResponse;
import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.Nullable;
import java.io.IOException;

public class FileAPI {
  private final RequestProcessor requestProcessor = new RequestProcessor();
  private static final Gson GSON = new GsonBuilder().create();

  public static void main(String[] args) throws IOException {
    FileAPI api = new FileAPI();
    User user = new User("WslF", "...");
    String repo = "Test";
    String filePath = "folder1/f2/f3/f4/test6.txt";
    String content = "this is content of file test 6!";
    api.createFile(user, repo, filePath, content, "commit test 5");
    String contentResponse = api.getFileContent(user, repo, filePath, null);
    if (content.equals(contentResponse)) {
      System.out.println("It works");
    }
  }

  public boolean createFile(User user, String repositoryName, String filePath, String content, String commitMessage) throws IOException {
    FileRequest createFileRequest = new CreateFileRequest(user, repositoryName, filePath, commitMessage, content);

    HttpResponse response = requestProcessor.sendPutRequest(createFileRequest);

    return response.isSuccessStatusCode();
  }

  public GetFileResponse getFile(User user, String repositoryName, String filePath, @Nullable String ref) throws IOException {
    GetFileRequest createFileRequest = new GetFileRequest(user, repositoryName, filePath, ref);

    HttpResponse httpResponse = requestProcessor.sendGetRequest(createFileRequest);

    return GSON.fromJson(httpResponse.parseAsString(), GetFileResponse.class);
  }

  public String getFileContent(User user, String repositoryName, String filePath, @Nullable String ref) throws IOException {
    GetFileResponse getFileResponse = getFile(user, repositoryName, filePath, ref);
    return getFileResponse.getContent();
  }

  public String getFileSHA(User user, String repositoryName, String filePath, @Nullable String ref) throws IOException {
    GetFileResponse getFileResponse = getFile(user, repositoryName, filePath, ref);
    return getFileResponse.getSha();
  }

}
