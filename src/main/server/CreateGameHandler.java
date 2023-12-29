package server;

import RequestResponses.CreateGameRequest;
import RequestResponses.CreateGameResult;
import com.google.gson.Gson;
import service.CreateGameService;
import spark.Request;
import spark.Response;

public class CreateGameHandler{
  public String handler(Request req, Response res){
    Gson gson = new Gson();

    CreateGameRequest request = gson.fromJson(req.body(), CreateGameRequest.class);
    request.setAuthToken(req.headers("Authorization"));
    CreateGameService service = new CreateGameService();
    CreateGameResult result = service.createGame(request);
    String josnString = gson.toJson(result);

    if(result.getMessage() == null){
      res.status(200);
    }else if(result.getMessage().equals("Error unauthorized")){
      res.status(401);
    }else if(result.getMessage().equals("Error: bad request")){
      res.status(400);
    }else{
      res.status(500);
      result.setMessage("Error: description");
    }

    return josnString;
  }
}
