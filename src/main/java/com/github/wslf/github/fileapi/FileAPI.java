package com.github.wslf.github.fileapi;

import com.github.wslf.github.User;
import com.github.wslf.github.fileapi.requests.*;
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
    String filePath = "folder1/f2/f3/f4/test8.txt";
    String content = "this is content of file test 8!";
    api.deleteFile(user, repo, filePath, "commit to delete", null);
    api.createFile(user, repo, filePath, content, "commit test 8");
    String contentResponse = api.getFileContent(user, repo, filePath, null);
    if (content.equals(contentResponse)) {
      System.out.println("It works");
    }
    api.updateFile(user, repo, filePath, content + " updated", "commit test 8", "master");

  }

  public boolean createFile(User user, String repositoryName, String filePath, String content, String commitMessage) throws IOException {
    FileRequest createFileRequest = new CreateFileRequest(user, repositoryName, filePath, commitMessage, content);

    HttpResponse response = requestProcessor.sendPutRequest(createFileRequest);

    return response.isSuccessStatusCode();
  }

  public GetFileResponse getFile(User user, String repositoryName, String filePath, @Nullable String ref) throws IOException {
    GetFileRequest getFileRequest = new GetFileRequest(user, repositoryName, filePath, ref);

    HttpResponse httpResponse = requestProcessor.sendRequest(getFileRequest, "GET");

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

  public boolean deleteFile(User user, String repositoryName, String filePath, String commitMessage, @Nullable String branch) throws IOException {
    String sha = getFileSHA(user, repositoryName, filePath, branch);
    DeleteFileRequest deleteFileRequest = new DeleteFileRequest(user, repositoryName, filePath, commitMessage, sha, branch);

    HttpResponse httpResponse = requestProcessor.sendRequest(deleteFileRequest, "DELETE");

    return httpResponse.isSuccessStatusCode();
  }

  public boolean updateFile(User user, String repositoryName, String filePath, String content, String commitMessage, String branch) throws IOException {
    String sha = getFileSHA(user, repositoryName, filePath, branch);

    FileRequest createFileRequest = new UpdateFileRequest(user, repositoryName, filePath, commitMessage, sha, content, branch);

    HttpResponse response = requestProcessor.sendPutRequest(createFileRequest);

    return response.isSuccessStatusCode();
  }
}
