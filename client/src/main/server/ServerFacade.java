package server;

import com.google.gson.Gson;

import RequestResponses.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ServerFacade {
  HttpURLConnection connection = null;
  String stringUrl = null;
  public ServerFacade(String urlString) {
    stringUrl = urlString;
  }

  public ClearResult doClear()throws IOException{
    URL url = new URL(stringUrl + "/db");
    connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("DELETE");
    connection.setDoOutput(true);

    connection.connect();

    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
      InputStream responseBody = connection.getInputStream();
      ClearResult result = new Gson().fromJson(new InputStreamReader(responseBody), ClearResult.class);
      return result;
    }else{
      InputStream responseBody = connection.getErrorStream();
      ClearResult result = new Gson().fromJson(new InputStreamReader(responseBody), ClearResult.class);
      return result;
    }

  }
  public LogoutResult doLogout(String authToken) throws IOException{
    URL url = new URL(stringUrl + "/session");
    connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("DELETE");
    connection.setDoOutput(true);
    connection.addRequestProperty("Accept", "application/json");
    connection.addRequestProperty("authorization", authToken);

    connection.connect();

    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
      InputStream responseBody = connection.getInputStream();
      LogoutResult result = new Gson().fromJson(new InputStreamReader(responseBody), LogoutResult.class);
      return result;
    }else{
      InputStream responseBody = connection.getErrorStream();
      LogoutResult result = new Gson().fromJson(new InputStreamReader(responseBody), LogoutResult.class);
      return result;
    }
  }

  public JoinGameResult doJoinGame(Integer id, String team, String authToken) throws IOException{
    URL url = new URL(stringUrl + "/game");
    connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("PUT");
    connection.setDoOutput(true);
    connection.addRequestProperty("Authorization", authToken);

    connection.connect();

    JoinGameRequest request = new JoinGameRequest();
    request.setAuthTokean(authToken);
    request.setGameID(id);
    request.setPlayerColor(team);
    try(OutputStream requestBody = connection.getOutputStream();){
      var jsonBody = new Gson().toJson(request);
      requestBody.write(jsonBody.getBytes());
    }
    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
      InputStream responseBody = connection.getInputStream();
      JoinGameResult result = new Gson().fromJson(new InputStreamReader(responseBody), JoinGameResult.class);
      return result;
    }else {
      InputStream responseBody=connection.getErrorStream();
      JoinGameResult result=new Gson().fromJson(new InputStreamReader(responseBody), JoinGameResult.class);
      return result;
    }
  }

  public CreateGameResult doCreateGame(String gameName, String authToken) throws IOException{
    URL url = new URL(stringUrl + "/game");
    connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);
    connection.addRequestProperty("Authorization", authToken);

    connection.connect();


    CreateGameRequest request = new CreateGameRequest();
    request.setGameName(gameName);
    request.setAuthToken(authToken);
    try(OutputStream requestBody = connection.getOutputStream();){
      var jsonBody = new Gson().toJson(request);
      requestBody.write(jsonBody.getBytes());
    }
    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
      InputStream responseBody = connection.getInputStream();
      CreateGameResult result = new Gson().fromJson(new InputStreamReader(responseBody), CreateGameResult.class);
      return result;
    }else{
      InputStream responseBody = connection.getErrorStream();
      CreateGameResult result = new Gson().fromJson(new InputStreamReader(responseBody), CreateGameResult.class);
      return result;
    }
  }

  public ListGamesResult doListGames(String authToken) throws IOException{
    URL url = new URL(stringUrl + "/game");
    connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("GET");
    connection.setDoOutput(true);

    connection.addRequestProperty("Authorization", authToken);

    connection.connect();

    ListGamesRequest request = new ListGamesRequest();
    request.setAuthToken(authToken);

    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
      InputStream responseBody = connection.getInputStream();
      ListGamesResult result = new Gson().fromJson(new InputStreamReader(responseBody), ListGamesResult.class);
      return result;
    }else{
      InputStream responseBody = connection.getErrorStream();
      ListGamesResult result = new Gson().fromJson(new InputStreamReader(responseBody), ListGamesResult.class);
      return result;
    }
  }

  public LoginResult doLogin(String username, String password) throws IOException{
    URL url = new URL(stringUrl + "/session");

    connection = (HttpURLConnection) url.openConnection();
    connection.setReadTimeout(5000);
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);
    connection.connect();

    LoginRequest request = new LoginRequest();
    request.setPassword(password);
    request.setUsername(username);
    try(OutputStream requestBody = connection.getOutputStream();){
      var jsonBody = new Gson().toJson(request);
      requestBody.write(jsonBody.getBytes());
    }

    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
      InputStream responseBody = connection.getInputStream();
      LoginResult result = new Gson().fromJson(new InputStreamReader(responseBody), LoginResult.class);
      return result;
    }else{
      InputStream responseBody = connection.getErrorStream();
      LoginResult result = new Gson().fromJson(new InputStreamReader(responseBody), LoginResult.class);
      return result;
    }
  }

  public RegisterResult doRegister(String username, String password, String email) throws IOException {
    URL url = new URL(stringUrl + "/user");
    connection = (HttpURLConnection) url.openConnection();connection.setReadTimeout(5000);
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);

    connection.connect();

    RegisterRequest request = new RegisterRequest();
    request.setUsername(username);
    request.setPassword(password);
    request.setEmail(email);
    try(OutputStream requestBody = connection.getOutputStream();){
      var jsonBody = new Gson().toJson(request);
      requestBody.write(jsonBody.getBytes());
    }
    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
      InputStream responseBody = connection.getInputStream();
      RegisterResult result = new Gson().fromJson(new InputStreamReader(responseBody), RegisterResult.class);
      return result;
    }else{
      InputStream responseBody = connection.getErrorStream();
      RegisterResult result = new Gson().fromJson(new InputStreamReader(responseBody), RegisterResult.class);
      return result;
    }


  }// doPost function

}
