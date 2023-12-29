package server;

import RequestResponses.ListGamesRequest;
import RequestResponses.ListGamesResult;
import com.google.gson.Gson;
import dataAccess.AuthDAO;
import service.ListGamesService;
import spark.Request;
import spark.Response;


public class ListGamesHandler{
  AuthDAO authDAO = new AuthDAO();

  public String handler(Request req, Response res){
    Gson gson = new Gson();

    ListGamesRequest request = new ListGamesRequest();
    request.setAuthToken(req.headers("Authorization"));
    ListGamesService service = new ListGamesService();
    ListGamesResult result = service.listGames(request);
    String josnString = gson.toJson(result);

    if(result.getMessage() == null){
      res.status(200);
    }else if(result.getMessage().equals("Error: unauthorized")){
      res.status(401);
    }else{
      res.status(500);
    }

    return josnString;
  }
}
