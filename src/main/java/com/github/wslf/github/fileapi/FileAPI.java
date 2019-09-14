package com.github.wslf.github.fileapi;

import com.github.wslf.github.Credentials;
import com.github.wslf.github.fileapi.requests.*;
import com.github.wslf.github.fileapi.responses.GetFileResponse;
import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * This class allows you to create, modify or delete file from your GitHub repository.
 * <p>
 * Usage example:
 *
 * <pre>
 * {@code
 *     FileAPI api = new FileAPI();
 *     Credentials credentials = new Credentials("WslF", "...");
 *     String repo = "Test";
 *     String filePath = "folder1/f2/f3/f4/test.txt";
 *     String content = "this is content of file test!";
 *     api.createFile(credentials, repo, filePath, content, "commit test");
 *     String contentResponse = api.getFileContent(credentials, repo, filePath, null);
 *     if (content.equals(contentResponse)) {
 *       System.out.println("It works");
 *     }
 *     api.updateFile(credentials, repo, filePath, content + " updated", "commit test", "master");
 *     api.deleteFile(credentials, repo, filePath, "commit to delete", null);
 * }
 * </pre>
 */
public class FileAPI {
  private final RequestProcessor requestProcessor = new RequestProcessor();
  private static final Gson GSON = new GsonBuilder().create();


  /**
   * Creates file in the GitHub repository.
   *
   * @param credentials    credentials object is used to authenticate.
   * @param repositoryName name of the repository
   * @param filePath       path to the file in the repository. It shouldn't contains repository name.
   * @param content        content of the file you'd like to create.
   * @param commitMessage  message for the commit.
   * @return shamefulness of the operation.
   * @throws IOException if network issues occur or file already exists.
   */
  public boolean createFile(Credentials credentials, String repositoryName, String filePath, String content,
                            String commitMessage) throws IOException {
    return createFile(credentials, repositoryName, filePath, content, commitMessage, null);
  }

  /**
   * Creates file in the GitHub repository.
   *
   * @param credentials    credentials object is used to authenticate.
   * @param repositoryName name of the repository
   * @param filePath       path to the file in the repository. It shouldn't contains repository name.
   * @param content        content of the file you'd like to create.
   * @param commitMessage  message for the commit.
   * @param branch         name of the brunch, you'd like to create file.
   * @return successfulness of the operation.
   * @throws IOException if network issues occur or file already exists.
   */
  public boolean createFile(Credentials credentials, String repositoryName, String filePath, String content,
                            String commitMessage, String branch) throws IOException {
    FileRequest createFileRequest =
        new CreateFileRequest(credentials, repositoryName, filePath, commitMessage, content, branch);

    HttpResponse response = requestProcessor.sendPutRequest(createFileRequest);

    return response.isSuccessStatusCode();
  }

  /**
   * Reads file from the GitHub repository.
   *
   * @param credentials    credentials object is used to authenticate.
   * @param repositoryName name of the repository
   * @param filePath       path to the file in the repository. It shouldn't contains repository name.
   * @param ref            name of the commit/branch/tag. Default: the repository’s default branch (usually master)
   * @return File's content and sha
   * @throws IOException if network issues occur or file doesn't exist.
   */
  public GetFileResponse getFile(Credentials credentials, String repositoryName, String filePath,
                                 @Nullable String ref) throws IOException {
    GetFileRequest getFileRequest = new GetFileRequest(credentials, repositoryName, filePath, ref);

    HttpResponse httpResponse = requestProcessor.sendRequest(getFileRequest, "GET");

    return GSON.fromJson(httpResponse.parseAsString(), GetFileResponse.class);
  }

  /**
   * Reads file's content from the GitHub repository.
   *
   * @param credentials    credentials object is used to authenticate.
   * @param repositoryName name of the repository
   * @param filePath       path to the file in the repository. It shouldn't contains repository name.
   * @param ref            name of the commit/branch/tag. Default: the repository’s default branch (usually master)
   * @return File's content
   * @throws IOException if network issues occur or file doesn't exist.
   */
  public String getFileContent(Credentials credentials, String repositoryName, String filePath,
                               @Nullable String ref) throws IOException {
    GetFileResponse getFileResponse = getFile(credentials, repositoryName, filePath, ref);
    return getFileResponse.getContent();
  }

  /**
   * Reads file's SHA from the GitHub repository.
   *
   * @param credentials    credentials object is used to authenticate.
   * @param repositoryName name of the repository
   * @param filePath       path to the file in the repository. It shouldn't contains repository name.
   * @param ref            name of the commit/branch/tag. Default: the repository’s default branch (usually master)
   * @return File's SHA
   * @throws IOException if network issues occur or file doesn't exist.
   */
  public String getFileSHA(Credentials credentials, String repositoryName, String filePath,
                           @Nullable String ref) throws IOException {
    GetFileResponse getFileResponse = getFile(credentials, repositoryName, filePath, ref);
    return getFileResponse.getSha();
  }

  /**
   * Deletes file from the GitHub repository.
   *
   * @param credentials    credentials object is used to authenticate.
   * @param repositoryName name of the repository
   * @param filePath       path to the file in the repository. It shouldn't contains repository name.
   * @param commitMessage  message for the commit.
   * @param branch         name of the brunch, you'd like to delete file.
   * @return successfulness of the operation.
   * @throws IOException if network issues occur or file doesn't exist.
   */
  public boolean deleteFile(Credentials credentials, String repositoryName, String filePath, String commitMessage,
                            @Nullable String branch) throws IOException {
    String sha = getFileSHA(credentials, repositoryName, filePath, branch);
    DeleteFileRequest deleteFileRequest =
        new DeleteFileRequest(credentials, repositoryName, filePath, commitMessage, sha, branch);

    HttpResponse httpResponse = requestProcessor.sendRequest(deleteFileRequest, "DELETE");

    return httpResponse.isSuccessStatusCode();
  }

  /**
   * Updates (changes) file content in the GitHub repository.
   *
   * @param credentials    credentials object is used to authenticate.
   * @param repositoryName name of the repository
   * @param filePath       path to the file in the repository. It shouldn't contains repository name.
   * @param content        new content of the file you'd like to create.
   * @param commitMessage  message for the commit.
   * @param branch         name of the brunch, you'd like to update file.
   * @return successfulness of the operation.
   * @throws IOException if network issues occur or file doesn't exist.
   */
  public boolean updateFile(Credentials credentials, String repositoryName, String filePath, String content,
                            String commitMessage, String branch) throws IOException {
    String sha = getFileSHA(credentials, repositoryName, filePath, branch);

    FileRequest createFileRequest =
        new UpdateFileRequest(credentials, repositoryName, filePath, commitMessage, sha, content, branch);

    HttpResponse response = requestProcessor.sendPutRequest(createFileRequest);

    return response.isSuccessStatusCode();
  }
}
