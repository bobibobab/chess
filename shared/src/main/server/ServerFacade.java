package server;

import com.google.gson.Gson;
import model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

public class ServerFacade {
  private final String serveraUrl;

  public ServerFacade(String url) {
    this.serveraUrl = url;
  }// constructor

  private <T> T makeRequest (String method, String path, Object request, Class<T> reponseClass){
    try{
      URL url = (new URI(serveraUrl + path)).toURL();
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setDoOutput(true);

      writeBody(request, http);
      http.connect();


    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  private static void writeBody(Object request, HttpURLConnection http) throws IOException {
    if(request != null){
      http.addRequestProperty("Content-Type", "application/json");
      String reqData = new Gson().toJson(request);
      try(OutputStream reqBody = http.getOutputStream()){
        reqBody.write(reqData.getBytes());
      }
    }
  }
  private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
    T response = null;
    if (http.getContentLength() < 0) {
      try (InputStream respBody = http.getInputStream()) {
        InputStreamReader reader = new InputStreamReader(respBody);
        if (responseClass != null) {
          Gson gson = new Gson();
          response = gson.fromJson(reader, responseClass);
        }
      }
    }
    return response;
  }
  public void login(User user){
    var path = "/session";
    this.makeRequest("POST", path,user, User.class);
  }

  public void register(User user){
    var path = "/user";
    this.makeRequest("POST", path,user, User.class);
  }
}
