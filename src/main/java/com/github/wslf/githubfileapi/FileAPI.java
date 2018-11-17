package com.github.wslf.githubfileapi;

import com.google.api.client.http.*;
import com.google.api.client.http.apache.ApacheHttpTransport;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.github.wslf.githubfileapi.Constants.CONTENT_TYPE;
import static com.github.wslf.githubfileapi.Constants.GITHUB_API_REPOS;

public class FileAPI {
  private static final HttpTransport HTTP_TRANSPORT = new ApacheHttpTransport();
  private static final HttpRequestFactory REQUEST_FACTORY = HTTP_TRANSPORT.createRequestFactory();

  public static void main(String[] args) throws IOException {
    FileAPI api = new FileAPI();
    GithubUser user = new GithubUser("WslF", "...");
    api.createFile(user, "Test", "folder1/test2.txt", "fileContent2", "commit2");
  }

  public boolean createFile(GithubUser user, String repositoryName, String filePath, String content, String commitMessage) throws IOException {
    CreateFileRequest createFileRequest = new CreateFileRequest(commitMessage, content);

    String url = buildUrl(user, repositoryName, filePath);
    String body = createFileRequest.buildBody();

    HttpResponse response = putRequest(user, url, body);

    return response.isSuccessStatusCode();
  }

  void addHeaders(HttpRequest request, GithubUser user) {
    HttpHeaders headers = request.getHeaders();
    //headers.set("Content-Type", CONTENT_TYPE);
    headers.set("Authorization", user.getAuth());
  }

  String buildUrl(GithubUser user, String repositoryName, String filePath) {
    return String.format("%s%s/%s/contents/%s", GITHUB_API_REPOS, user.getName(), repositoryName, filePath);
  }

  private HttpResponse putRequest(GithubUser user, String url, String body) throws IOException {
    HttpContent content = new ByteArrayContent(CONTENT_TYPE, body.getBytes(StandardCharsets.UTF_8));

    HttpRequest request = REQUEST_FACTORY.buildPutRequest(new GenericUrl(url), content);

    request.getHeaders().setAuthorization(user.getAuth());
    //request.getHeaders().setContentType()

    return request.execute();
  }
}
