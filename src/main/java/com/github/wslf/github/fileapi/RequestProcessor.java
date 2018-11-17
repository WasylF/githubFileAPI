package com.github.wslf.github.fileapi;

import com.github.wslf.github.fileapi.requests.FileRequest;
import com.google.api.client.http.*;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.gson.GsonFactory;

import javax.annotation.Nullable;
import java.io.IOException;

public class RequestProcessor {
  private static final HttpTransport HTTP_TRANSPORT = new ApacheHttpTransport();
  private static final GsonFactory GSON_FACTORY = new GsonFactory();
  private static final HttpRequestFactory REQUEST_FACTORY = HTTP_TRANSPORT.createRequestFactory();

  public HttpResponse sendRequest(FileRequest fileRequest, String requestMethod) throws IOException {

    HttpRequest request = REQUEST_FACTORY.buildRequest(requestMethod, fileRequest, null);

    return sendRequest(request, fileRequest);
  }

  public HttpResponse sendPutRequest(FileRequest fileRequest) throws IOException {
    JsonHttpContent httpContent = new JsonHttpContent(GSON_FACTORY, fileRequest);
    httpContent.setMediaType(new HttpMediaType(Constants.CONTENT_TYPE));

    HttpRequest request = REQUEST_FACTORY.buildPutRequest(fileRequest, httpContent);
    return sendRequest(request, fileRequest);
  }

  private HttpResponse sendRequest(HttpRequest httpRequest, FileRequest fileRequest) throws IOException {
    addHeaders(httpRequest, fileRequest.getAuth());

    return httpRequest.execute();
  }

  private void addHeaders(HttpRequest request, @Nullable String auth) {
    if (auth != null) {
      request.getHeaders().setAuthorization(auth);
    }
    request.getHeaders().setContentType(Constants.CONTENT_TYPE);
  }

}
